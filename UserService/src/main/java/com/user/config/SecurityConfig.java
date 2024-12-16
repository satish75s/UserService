
package com.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

	// authentication
	@Bean
	UserDetailsService userDetailsService() {
		/*UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN")
				.build();
		UserDetails user = User.withUsername("user").password(passwordEncoder().encode("user123")).roles("USER")
				.build();// .roles("USER", "ADMIN", "HR").roles("USER").build();
		UserDetails user1 = User.withUsername("temp").password(passwordEncoder().encode("temp123")).build();
		return new InMemoryUserDetailsManager(admin, user, user1);*/
		return new UserInfoUserDetailsService();

	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Authorization
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		/*
		 * allows all start with /login without any role but permits /login/user and
		 * /login/admin with user roles "USER" and "ADMIN"
		 */

		http.securityMatcher("/login/**").csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/login/user").hasRole("USER")
						.requestMatchers("/login/admin").hasRole("ADMIN").anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults()).formLogin(Customizer.withDefaults());
		return http.build();

	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

}
