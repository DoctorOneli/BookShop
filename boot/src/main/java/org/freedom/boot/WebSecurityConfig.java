package org.freedom.boot;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freedom.boot.bean.Msg;
import org.freedom.boot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 安全设置，权限管理
 * @author oneli
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AdminService adminService;
	
	 @Bean
	    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(adminService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/client/**").permitAll()
				.antMatchers("/adminbook/**").hasRole("BOOK_ADMIN")
				.antMatchers("/adminorder/**").hasRole("ORDER_ADMIN")
				.antMatchers("/adminuser/**").hasRole("USER_ADMIN")
				.antMatchers("/adminrole/**").hasRole("HYPER_ADMIN")
				.anyRequest().authenticated()// 其他的路径都是登录后即可访问
				.and().formLogin().loginPage("/book/login.html").loginProcessingUrl("/login")
				.usernameParameter("username").passwordParameter("password")
				.successHandler(new AuthenticationSuccessHandler() {
					@Override
					public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
							HttpServletResponse httpServletResponse, Authentication authentication)
							throws IOException, ServletException {
						httpServletResponse.setContentType("application/json;charset=utf-8");
						PrintWriter out = httpServletResponse.getWriter();
						out.write("{\"status\":\"200\",\"msg\":\"登录成功\"}");
						out.flush();
						out.close();
					}
				}).failureHandler(new AuthenticationFailureHandler() {
					@Override
					public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
							HttpServletResponse httpServletResponse, AuthenticationException e)
							throws IOException, ServletException {
						httpServletResponse.setContentType("application/json;charset=utf-8");
						PrintWriter out = httpServletResponse.getWriter();
						out.write("{\"status\":\"500\",\"msg\":\"登录失败\"}");
						out.flush();
						out.close();
					}
				}).permitAll().and().logout().logoutUrl("/logout").clearAuthentication(true).invalidateHttpSession(true)
				.addLogoutHandler(new LogoutHandler() {

					@Override
					public void logout(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) {
						// TODO Auto-generated method stub

					}
				}).logoutSuccessHandler(new LogoutSuccessHandler() {

					@Override
					public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
						response.setContentType("application/json;charset=utf-8");
						PrintWriter out = response.getWriter();
						out.write("{\"status\":\"200\",\"msg\":\"注销成功\"}");
						out.flush();
						out.close();

					}
				}).and().csrf().disable();
	}

	public void configure(WebSecurity web) throws Exception {
		// 忽略静态资源URL
		web.ignoring().antMatchers("/book/static/**","/upload/**");

	}

}
