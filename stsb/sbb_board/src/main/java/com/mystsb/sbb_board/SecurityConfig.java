package com.mystsb.sbb_board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration.ProviderDetails.UserInfoEndpoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.mystsb.sbb_board.oauth.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((AuthorizeHttpRequestsConfigurer) -> AuthorizeHttpRequestsConfigurer
				.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
			.csrf((csrf) -> csrf
				.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
			.headers((headers) -> headers
				.addHeaderWriter(new XFrameOptionsHeaderWriter(
						XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
			.formLogin((formLogin) -> formLogin
					.loginPage("/user/login")
					.defaultSuccessUrl("/"))
			.oauth2Login((oauth2Login) -> oauth2Login
					.loginPage("/user/login")
			.userInfoEndpoint((userInfoEndpoint) -> userInfoEndpoint
					.userService(principalOauth2UserService)))
					
			.logout((logout) -> logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true));
			
			
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
}
