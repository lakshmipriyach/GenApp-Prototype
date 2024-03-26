package com.prototype.genapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.prototype.genapp.security.JwtAuthenticationEntryPoint;
import com.prototype.genapp.security.JwtAuthenticationFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private UserDetailsService userDetailsService;

	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	private JwtAuthenticationFilter authenticationFilter;

	public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationEntryPoint authenticationEntryPoint,
			JwtAuthenticationFilter authenticationFilter) {
		this.userDetailsService = userDetailsService;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.authenticationFilter = authenticationFilter;
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors().and()
		.csrf().disable()
				.authorizeHttpRequests((authorize) ->
				authorize
				.requestMatchers(HttpMethod.POST, "/customer/register").permitAll()
				.requestMatchers(HttpMethod.POST, "/customer/login").permitAll()
						.requestMatchers(HttpMethod.GET, "/customer/{customernumber}").authenticated()
						.requestMatchers(HttpMethod.GET, "/customer").authenticated()
						.requestMatchers(HttpMethod.PUT, "/customer/update/{customernumber}").authenticated()
						.requestMatchers(HttpMethod.DELETE, "/customer/delete/{customernumber}").authenticated()
						.requestMatchers(HttpMethod.GET, "/motor/{policyNumber}/{customerNumber}").authenticated()
						.requestMatchers(HttpMethod.GET, "/motor/policies/{customerNumber}").authenticated()
						.requestMatchers(HttpMethod.POST, "/motor/addpolicy").authenticated()
						.requestMatchers(HttpMethod.PUT, "/motor//{policyNumber}/{customerNumber}").authenticated()
						.requestMatchers(HttpMethod.DELETE, "/motor/{policyNumber}/{customerNumber}").authenticated()
						.requestMatchers(HttpMethod.POST,"/claims/addclaim").authenticated()
						.requestMatchers(HttpMethod.GET,"/claims/{claimNumber}").authenticated()
						.requestMatchers(HttpMethod.GET,"/claims").authenticated()
						.requestMatchers(HttpMethod.PUT,"/claims/{claimNumber}").authenticated()
						.requestMatchers(HttpMethod.DELETE,"/claims/{claimNumber}").authenticated()
						.requestMatchers(HttpMethod.GET,"/search").permitAll()
						.requestMatchers(HttpMethod.POST,"/chat").permitAll()
						.requestMatchers("/v3/api-docs/**").permitAll()
						.requestMatchers("/swagger-ui/**").permitAll()
						.anyRequest().permitAll())
				.httpBasic(Customizer.withDefaults())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	
	@Bean
	public OpenAPI customOpenAPI() {
	    return new OpenAPI()
	            .components(new Components()
	                    .addSecuritySchemes("basicScheme", new SecurityScheme()
	                            .type(SecurityScheme.Type.HTTP)
	                            .scheme("basic")))
	            .addSecurityItem(new SecurityRequirement().addList("basicScheme"));
	}

	 

}
