package com.kosta.KOSTA_3_final.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.kosta.KOSTA_3_final.service.user.UserService;
import lombok.extern.java.Log;
@Log
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	UserService UserService; 
	
	@Autowired
	DataSource datasource;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();   //Spring Security에서 제공하는 비밀번호 암호화 객체
	}
	@Override  //WebSecurity를 통해 HTTP 요청에 대한 웹 기반 보안을 구성
	public void configure(WebSecurity web) throws Exception {
		// 파일 기준은 resources/static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )

		web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/lib/**","/assets/**","/error");

	}
	
	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
		DefaultHttpFirewall firewall = new DefaultHttpFirewall();
		firewall.setAllowUrlEncodedSlash(true);
		return firewall;
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		log.info("security config..........");
		// antMatchers url 패턴에 대한 접근허용
		// permitAll: 모든사용자가 접근가능하다는 의미
		// hasRole : 특정권한을 가진 사람만 접근가능하다는 의미
		http.authorizeRequests() // HttpServletRequest에 따라 접근(access)을 제한
				.antMatchers("/Home/**", "/auth/**","/login/**","/index").permitAll() //   누구나 접근 허용
				.antMatchers("/admin/**").hasRole("ADMIN") // /admin으로 시작하는 경로는  ADMIN롤을 가진 사용자만  접근 가능(자동으로 ROLE_가 삽입)
				.anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
				.and().formLogin() // form 기반으로 인증을 하도록 한다. 로그인 정보는 기본적으로 HttpSession을 이용
				.loginPage("/login") // 로그인 페이지 링크 .... post의 이름이 같다면 loginProcessingUrl생략 
				.defaultSuccessUrl("/index") // 로그인 성공 후 리다이렉트 주소	
				.failureUrl("/login?error") //로그인 실패시 에러 메세지
                //스프링시큐리티가 해당주소로 오는 요청을 가로채서 대신한다. 
.permitAll()
.and()
.logout() // 로그아웃에 관한 설정을 의미
.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
.logoutSuccessUrl("/login") // 로그아웃 성공시 리다이렉트 주소
.invalidateHttpSession(true)  // 세션 지우기
.and().csrf().disable();  //csrf(크로스사이트 위조요청에 대한 설정) 토큰 비활성화 (test시에는 disable권장)            
http.exceptionHandling().accessDeniedPage("/accessFail"); // 403 예외처리 핸들링   권한이 없는 대상이 접속을시도했을 때
http.rememberMe().key("RememberU").userDetailsService(UserService).tokenRepository(tokenRepository()).tokenValiditySeconds(60*60*24);//로그인 유지 기능
	}
	
	@Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(datasource);
        return jdbcTokenRepository;
    }

}