package net.softbell.bsh.component;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import net.softbell.bsh.util.CookieUtil;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증관련 JWT 토큰 생성 및 검증 컴포넌트
 */
@RequiredArgsConstructor
@Component
public class JwtTokenProvider
{
    @Value("${spring.jwt.secret}")
    private String secretKey;
    private long tokenValidMilisecond = 1000L * 60 * 60; // 1시간만 토큰 유효
    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init()
    {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    
    public void setCookieAuth(HttpServletResponse response, Authentication authentication)
    {
    	CookieUtil.create(response, "X-AUTH-TOKEN", createToken(authentication), false, false, 60 * 60);
    }

    // Jwt 토큰 생성
    public String createToken(String userPk, Collection<? extends GrantedAuthority> roles)
    {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();
    }
    
    public String createToken(Authentication authentication)
    {
        Claims claims = Jwts.claims().setSubject(authentication.getName());
        claims.put("roles", authentication.getAuthorities());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();
    }

    // Jwt 토큰으로 인증 정보를 조회
    public Authentication getAuthentication(String token)
    {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰에서 회원 구별 정보 추출
    public String getUserPk(String token)
    {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    
    public boolean isApiMode(HttpServletRequest request)
    {
    	if (request.getRequestURI().startsWith("/api/") || request.getRequestURI().startsWith("/ws/"))
    		return true;
    	return false;
    }

    // Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
    public String resolveToken(HttpServletRequest request)
    {
    	// Field
    	String token;
    	
    	// Load
    	if (isApiMode(request)) // API 및 웹소켓 경로면 헤더에서 인증정보 로드 (CSRF 미사용)
    		token = request.getHeader("X-AUTH-TOKEN");
    	else // 일반 유저 경로면 쿠키에서 인증정보 로드 (CSRF 사용)
    		token = CookieUtil.getValue(request, "X-AUTH-TOKEN");
    	
    	// Return
        return token;
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken)
    {
        try
        {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
