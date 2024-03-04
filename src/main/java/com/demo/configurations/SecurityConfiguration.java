package com.demo.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		return http.cors(cor -> cor.disable())
				.csrf(cs -> cs.disable())
				.authorizeHttpRequests(auth ->{
					auth
					.requestMatchers("/", "/admin/**", "/assets/**").permitAll();
				})
				.formLogin(formLogin -> {
					formLogin.loginPage("/admin/account/login")
					.loginProcessingUrl("admin/account/process-login");
				}).build();
	}
	
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
