
package com.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.ToString;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@ToString
public class SecurityConfig {
    //Authentication
	@Bean
	UserDetailsService userDetailsService() throws Exception {
       /*
        * in-memory credentials 
		UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("admin123"))
				.roles("USER", "ADMIN").build();
		UserDetails user = User.withUsername("user").password(passwordEncoder().encode("user123")).roles("USER")
				.build();
		UserDetails normalUser1 = User.withUsername("temp").password(passwordEncoder().encode("temp123")).build();
		return new InMemoryUserDetailsManager(admin, user, normalUser1);*/
		
		return new UserInfoUserDetailsService(); //DB Credentials
		
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
	//Authorization
	@Bean
	SecurityFilterChain securedFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			     .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults());
		return http.build();
	}
	
	@Bean  
	AuthenticationProvider authenticationProvider() throws Exception {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
}
