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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;
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
        web.ignoring().antMatchers("/rss/**", "/files/**");
		//web.ignoring().antMatchers("/**"); ///////////////// 임시 보안 전체 해제
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		// Common
    	http.authorizeRequests()
			        // 페이지 권한 설정
					.antMatchers("/admin/**").hasRole("ADMIN")
					//.antMatchers("/member/**").hasRole("MEMBER")
					//.antMatchers("/wss/**").hasRole("MEMBER")
					.antMatchers("/**").permitAll()
				.and() // 로그인 설정
					.formLogin()
					.loginPage("/login")
					//.successHandler(successHandler())
					//.failureHandler(failHandler())
					.permitAll()
				.and() // 로그아웃 설정
					.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true)
				.and()
			        // 403 예외처리 핸들링
					.exceptionHandling().accessDeniedPage("/denied")
				;
//    	http.csrf().disable(); // API 개발중 임시 해제 ////////////////////// TODO
    	
    	// Dev Mode
    	if (isDevMode())
    	{
	    	http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
	    	http.csrf().disable();
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
	
	/*@Bean
    public AuthenticationSuccessHandler successHandler() {
    	return new LoginSuccessHandler("/");
    }
    
    @Bean
    public AuthenticationFailureHandler failHandler() {
    	return new LoginFailureHandler("/login?error");
    }*/
}