package net.softbell.bsh.component

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lombok.RequiredArgsConstructor
import net.softbell.bsh.config.CustomConfig
import net.softbell.bsh.service.CenterService
import net.softbell.bsh.service.MemberService
import net.softbell.bsh.util.CookieUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증관련 JWT 토큰 생성 및 검증 컴포넌트
 */
@RequiredArgsConstructor
@Component
class JwtTokenProvider constructor() {
    @Value("\${bsh.security.jwt.secret.key}")
    private var secretKey: String? = null
    private val tokenValidMilisecond: Long = 1000L * 60 * 60 // 1시간만 토큰 유효
    private val memberService: MemberService? = null
    private val centerService: CenterService? = null
    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey!!.toByteArray())
    }

    fun setCookieAuth(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        // Field
        var maxAge: Int
        var multiple: Int
        val strAutoLogin: String?

        // Init
        maxAge = 60 * 60
        multiple = 1
        strAutoLogin = CookieUtil.getValue(request, CustomConfig.AUTO_LOGIN_COOKIE_NAME)

        // Check
        if (strAutoLogin != null && strAutoLogin.equals("1", ignoreCase = true)) multiple = 24 * 7
        maxAge *= multiple

        // Create
        create(response, CustomConfig.SECURITY_COOKIE_NAME, createToken(authentication, multiple), false, false, maxAge)
    }

    // Jwt 토큰 생성
    fun createToken(userPk: String?, roles: Collection<GrantedAuthority?>?): String {
        val claims: Claims = Jwts.claims().setSubject(userPk)
        claims.put("roles", roles)
        val now: Date = Date()
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(Date(now.getTime() + tokenValidMilisecond)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact()
    }

    fun createToken(authentication: Authentication?, multiple: Int): String {
        // Field
        var multiple: Int = multiple
        val claims: Claims = Jwts.claims().setSubject(authentication!!.getName())

        // Init
        claims.put("roles", authentication.getAuthorities())
        val now: Date = Date()

        // Exception
        if (multiple < 1) multiple = 1

        // Return
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(Date(now.getTime() + tokenValidMilisecond * multiple)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact()
    }

    // Jwt 토큰으로 인증 정보를 조회
    fun getAuthentication(token: String?): Authentication {
        val userDetails: UserDetails? = memberService!!.tokenLoadUserByUsername(getUserPk(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails!!.getAuthorities())
    }

    fun checkMaintenanceLogin(auth: Authentication?): Boolean {
        if (centerService.getSetting().getWebMaintenance() === 1) for (role: GrantedAuthority in auth!!.getAuthorities()) if ((!(role.getAuthority() == "ROLE_SUPERADMIN") &&
                        !(role.getAuthority() == "ROLE_ADMIN") &&
                        !(role.getAuthority() == "ROLE_NODE"))) return false
        return true
    }

    // Jwt 토큰에서 회원 구별 정보 추출
    fun getUserPk(token: String?): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject()
    }

    fun isApiMode(request: HttpServletRequest): Boolean {
        if (request.getRequestURI().startsWith("/api/") || request.getRequestURI().startsWith("/ws/")) return true
        return false
    }

    // Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
    fun resolveToken(request: HttpServletRequest): String? {
        // Field
        val token: String?

        // Load
        if (isApiMode(request)) // API 및 웹소켓 경로면 헤더에서 인증정보 로드 (CSRF 미사용)
            token = request.getHeader(CustomConfig.SECURITY_HEADER_NAME) else  // 일반 유저 경로면 쿠키에서 인증정보 로드 (CSRF 사용)
            token = CookieUtil.getValue(request, CustomConfig.SECURITY_COOKIE_NAME)

        // Return
        return token
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    fun validateToken(jwtToken: String?): Boolean {
        try {
            val claims: Jws<Claims> = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken)
            return !claims.getBody().getExpiration().before(Date())
        } catch (e: Exception) {
            return false
        }
    }
}