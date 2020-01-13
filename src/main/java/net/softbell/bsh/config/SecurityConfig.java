package net.softbell.bsh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;
import net.softbell.bsh.handler.LoginFailureHandler;
import net.softbell.bsh.handler.LoginSuccessHandler;
import net.softbell.bsh.handler.security.CustomAccessDeniedHandler;
import net.softbell.bsh.handler.security.CustomAuthenticationEntryPoint;
import net.softbell.bsh.handler.security.JwtAuthenticationFilter;
import net.softbell.bsh.handler.security.JwtTokenProvider;
import net.softbell.bsh.service.MemberService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 보안 설정
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private MemberService memberService;
	private final Environment env;
	private final JwtTokenProvider jwtTokenProvider;
    
    private boolean isDevMode() {
        String profile = env.getActiveProfiles().length > 0? env.getActiveProfiles()[0] : "dev";
        return profile.equals("dev");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
	@Override
	public void configure(WebSecurity web) throws Exception
	{
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/rss/**", "/files/**", "/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/h2-console/**", "/favicon.ico");
		//web.ignoring().antMatchers("/**"); ///////////////// 임시 보안 전체 해제
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		// Common
		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
    		.authorizeRequests() // 페이지 권한 설정
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/login", "/logout", "/signup", "/**/signin", "/**/signup").permitAll()
				.anyRequest().hasRole("MEMBER")
		.and()
			.csrf() // CSRF 설정
				.ignoringAntMatchers("/api/**")
		.and()
			.formLogin() // 폼 로그인 설정
				.loginPage("/login")
				.successHandler(successHandler())
				.failureHandler(failHandler())
				.permitAll()
		.and()
			.logout() // 로그아웃 설정
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
				.invalidateHttpSession(true)
				.deleteCookies("X-AUTH-TOKEN")
		.and()
            .exceptionHandling() // 예외 핸들링
            	.accessDeniedHandler(CustomAccessDeniedHandler.builder().G_API_URI("/api/rest/v1/accessdenied").G_VIEW_URI("/denied").build())
            	.authenticationEntryPoint(CustomAuthenticationEntryPoint.builder().G_API_URI("/api/rest/v1/entrypoint").G_VIEW_URI("/login").build())
        .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // JWT Token 필터
		;
    	
    	// Dev Mode
    	if (isDevMode())
    	{
	    	http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
	    	//http.csrf().disable();
	    	http.headers().frameOptions().disable();
    	}
	}
	
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
         return super.authenticationManagerBean();
    }
   
    /*@Bean
    public HttpSessionStrategy httpSessionStrategy() {
              return new HeaderHttpSessionStrategy();
    }*/
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	}
	
	@Bean
    public AuthenticationSuccessHandler successHandler() {
    	return new LoginSuccessHandler("/");
    }
    
    @Bean
    public AuthenticationFailureHandler failHandler() {
    	return new LoginFailureHandler("/login?error");
    }
}