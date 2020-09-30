package net.softbell.bsh.component

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import net.softbell.bsh.config.CustomConfig
import net.softbell.bsh.service.CenterService
import net.softbell.bsh.service.MemberService
import net.softbell.bsh.util.CookieUtil.create
import net.softbell.bsh.util.CookieUtil.getValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증관련 JWT 토큰 생성 및 검증 컴포넌트
 */
@Component
class JwtTokenProvider {
    @Value("\${bsh.security.jwt.secret.key}")
    private var secretKey: String? = null
    private val tokenValidMilisecond = 1000L * 60 * 60 // 1시간만 토큰 유효
    @Autowired lateinit var memberService: MemberService
    @Autowired lateinit var centerService: CenterService
    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey!!.toByteArray())
    }

    fun setCookieAuth(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication) {
        // Field
        var maxAge: Int
        var multiple: Int
        val strAutoLogin: String?

        // Init
        maxAge = 60 * 60
        multiple = 1
        strAutoLogin = getValue(request, CustomConfig.AUTO_LOGIN_COOKIE_NAME)

        // Check
        if (strAutoLogin != null && strAutoLogin.equals("1", ignoreCase = true)) multiple = 24 * 7
        maxAge *= multiple

        // Create
        create(response!!, CustomConfig.SECURITY_COOKIE_NAME, createToken(authentication, multiple), false, false, maxAge)
    }

    // Jwt 토큰 생성
    fun createToken(userPk: String?, roles: Collection<GrantedAuthority?>): String {
        val claims = Jwts.claims().setSubject(userPk)
        claims["roles"] = roles
        val now = Date()
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(Date(now.time + tokenValidMilisecond)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact()
    }

    fun createToken(authentication: Authentication, multiple: Int): String {
        // Field
        var multiple = multiple
        val claims = Jwts.claims().setSubject(authentication.name)

        // Init
        claims["roles"] = authentication.authorities
        val now = Date()

        // Exception
        if (multiple < 1) multiple = 1

        // Return
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(Date(now.time + tokenValidMilisecond * multiple)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact()
    }

    // Jwt 토큰으로 인증 정보를 조회
    fun getAuthentication(token: String?): Authentication {
        val userDetails = memberService.tokenLoadUserByUsername(getUserPk(token))

        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails?.authorities)
    }

    fun checkMaintenanceLogin(auth: Authentication): Boolean {
        if (centerService.getSetting().webMaintenance == 1.toByte())
            for (role in auth.authorities) if (role.authority != "ROLE_SUPERADMIN" &&
                role.authority != "ROLE_ADMIN" &&
                role.authority != "ROLE_NODE") return false
        return true
    }

    // Jwt 토큰에서 회원 구별 정보 추출
    fun getUserPk(token: String?): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    fun isApiMode(request: HttpServletRequest): Boolean {
        return if (request.requestURI.startsWith("/api/") || request.requestURI.startsWith("/ws/")) true else false
    }

    // Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
    fun resolveToken(request: HttpServletRequest): String? {
        // Field
        val token: String?

        // Load
        token = if (isApiMode(request)) // API 및 웹소켓 경로면 헤더에서 인증정보 로드 (CSRF 미사용)
            request.getHeader(CustomConfig.SECURITY_HEADER_NAME) else  // 일반 유저 경로면 쿠키에서 인증정보 로드 (CSRF 사용)
            getValue(request, CustomConfig.SECURITY_COOKIE_NAME)

        // Return
        return token
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    fun validateToken(jwtToken: String?): Boolean {
        return try {
            val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }
}