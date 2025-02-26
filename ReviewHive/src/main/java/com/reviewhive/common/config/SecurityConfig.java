package com.reviewhive.common.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
//	@Bean
//	protected AuthenticationSuccessHandler authenticationSuccessHandler() {
//		return new CustomSuccessHandler();
//	}
//
//	@Bean
//	protected AuthenticationFailureHandler authenticationFailureHandler() {
//		return new CustomerFailureHandler();
//	}
	
	@Autowired
	private DataSource dataSource;
	
	private static final String USER_ACCOUNT_SQL = "SELECT USERNAME,PASSWORD,TRUE"
			+ " FROM M_USER "
			+ " WHERE USERNAME = ?"
			+ " AND DELETE_FLG = FALSE ";

	private static final String USER_ROLE_SQL = "SELECT USERNAME, ROLE FROM M_USER WHERE USERNAME = ?";
	
	@Bean
	protected UserDetailsManager userDetailsService() {

		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

		users.setUsersByUsernameQuery(USER_ACCOUNT_SQL);
		users.setAuthoritiesByUsernameQuery(USER_ROLE_SQL);

		return users;
	}

	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/ping").permitAll()
						.requestMatchers("/view/**").permitAll()
						.requestMatchers("/").permitAll()
						.requestMatchers("/css/**").permitAll()
						.requestMatchers("/js/**").permitAll()
						.requestMatchers("/favicon.ico").permitAll()
						.requestMatchers("/icons/**").permitAll()
						.requestMatchers("/images/**").permitAll()
						.requestMatchers("/fonts/**").permitAll()
						.requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
						.requestMatchers("/questionaire/**").permitAll()
						.requestMatchers("/retrieve/**").permitAll()
						.anyRequest().authenticated())
				.formLogin((form) -> form
						.loginPage("/admin/login")
						.permitAll()
						 .failureUrl("/admin/login")
						//.failureHandler(authenticationFailureHandler())
						.usernameParameter("username")
						.passwordParameter("password")
						.defaultSuccessUrl("/admin/home"))
						//.successHandler(authenticationSuccessHandler()))
				.logout((logout) -> logout
						.logoutSuccessUrl("/admin/login")
						.invalidateHttpSession(true)
						.permitAll());

		return http.build();
	}
}
