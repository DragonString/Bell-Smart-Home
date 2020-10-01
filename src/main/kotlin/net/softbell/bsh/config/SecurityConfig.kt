package net.softbell.bsh.config

import net.softbell.bsh.component.JwtTokenProvider
import net.softbell.bsh.filter.security.JwtAuthenticationFilter
import net.softbell.bsh.handler.security.CustomAccessDeniedHandler
import net.softbell.bsh.handler.security.CustomAuthenticationEntryPoint
import net.softbell.bsh.handler.security.LoginFailureHandler
import net.softbell.bsh.handler.security.LoginSuccessHandler
import net.softbell.bsh.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 보안 설정
 */
@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired private lateinit var memberService: MemberService
    @Autowired private lateinit var env: Environment
    @Autowired private lateinit var jwtTokenProvider: JwtTokenProvider


    private fun isLocalMode(): Boolean {
        val profile = if (env.activeProfiles.isNotEmpty()) env.activeProfiles[0] else "local"
        return profile == "local"
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/rss/**", "/files/**", "/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/h2-console/**", "/favicon.ico")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        // Common
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // 페이지 권한 설정
                .antMatchers("/login", "/logout", "/signup", "/**/signin", "/**/signup",  // 계정 인증
                        "/api/rest/exception/**",  // 권한 예외
                        "/api/rest/*/auth/**",  // API 인증
                        "/api/rest/*/status/**",  // 서버 Status
                        "/api/rest/*/iot/auth/**",  // IoT API 인증
                        "/api/rest/*/interlock/**" // 연동 Webhook
                ).permitAll() // 누구나 접근 가능
                .antMatchers("/denied").authenticated() // 접근제한 페이지 인증
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN") // 관리자 페이지 인증
                .antMatchers("/ws/**").hasRole("NODE") // WebSocket 인증
                .anyRequest().hasAnyRole("MEMBER", "ADMIN", "SUPERADMIN") // 기타 모든 페이지는 Member 권한 보유자만 가능
                .and()
                .csrf() // CSRF 설정
                .csrfTokenRepository(CookieCsrfTokenRepository())
                .ignoringAntMatchers("/api/**") // API는 CSRF 사용 안함 (헤더로 인증하기 때문)
                .and()
                .formLogin() // 폼 로그인 설정
                .loginPage("/login")
                .usernameParameter("userId")
                .passwordParameter("password")
                .successHandler(successHandler())
                .failureHandler(failHandler())
                .permitAll()
                .and()
                .logout() // 로그아웃 설정
                .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies(CustomConfig.SECURITY_COOKIE_NAME, CustomConfig.AUTO_LOGIN_COOKIE_NAME)
                .and()
                .exceptionHandling() // 예외 핸들링
                .accessDeniedHandler(CustomAccessDeniedHandler("/api/rest/exception/denied", "/denied"))
                .authenticationEntryPoint(CustomAuthenticationEntryPoint("/api/rest/exception/entrypoint", "/login"))
                .and()
                .addFilterBefore(JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java) // JWT Token 필터

        // Dev Mode
        if (isLocalMode()) {
            http.authorizeRequests() // 페이지 권한 설정
                    .antMatchers("/h2-console/**")
                    .permitAll()
                    .and()
                    .csrf() // CSRF 설정
                    .ignoringAntMatchers("/h2-console/**")
                    .and()
                    .headers() // 헤더 설정
                    .frameOptions()
                    .disable()
        }
    }

    @Throws(Exception::class)
    public override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder())
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    /*@Bean
    public HttpSessionStrategy httpSessionStrategy()
    {
              return new HeaderHttpSessionStrategy();
    }*/
    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder?) {
    }

    @Bean
    fun successHandler(): AuthenticationSuccessHandler {
        return LoginSuccessHandler("/")
    }

    @Bean
    fun failHandler(): AuthenticationFailureHandler {
        return LoginFailureHandler("/login?error")
    }
}