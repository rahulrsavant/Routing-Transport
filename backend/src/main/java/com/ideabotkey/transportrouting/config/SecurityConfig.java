// package com.ideabotkey.transportrouting.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.Customizer;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// /**
// * Basic Spring Security configuration.
// * <p>
// * - Disables CSRF for APIs.
// * - Enables CORS using the configuration defined in {@link WebConfig}.
// * - Marks login and registration endpoints as public while requiring
// * authentication for any other request.
// */
// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
// Exception {
// http
// .cors(Customizer.withDefaults())
// .csrf(csrf -> csrf.disable())
// .authorizeHttpRequests(auth -> auth
// .requestMatchers(
// "/api/v1/users/login",
// "/api/v1/users/register",
// "/v3/api-docs/**",
// "/swagger-ui/**")
// .permitAll()
// .anyRequest().authenticated())
// .httpBasic(Customizer.withDefaults());
// return http.build();
// }
// }
