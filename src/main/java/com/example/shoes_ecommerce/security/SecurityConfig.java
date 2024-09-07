package com.example.shoes_ecommerce.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

@AllArgsConstructor
@Configuration
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Bean
    JwtAuthenticationProvider configJwtAuthenticationProvider(@Qualifier("refreshTokenJwtDecoder") JwtDecoder refreshTokenJwtDecoder) {
        JwtAuthenticationProvider auth = new JwtAuthenticationProvider(refreshTokenJwtDecoder);
        return auth;
    }

    @Bean
    DaoAuthenticationProvider configDaoAuthenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, @Qualifier("accessTokenJwtDecoder") JwtDecoder jwtDecoder) throws Exception {

        //Endpoint Security Config
        http.authorizeHttpRequests(
                endpoint->endpoint
                                .requestMatchers("/api/v1/auth/**",
                                        "/api/v1/upload/**",
                                        "/upload/**",
                                        "/api/v1/shop_infos/**"
                                )
                                .permitAll()

//                         Category
                        .requestMatchers(HttpMethod.GET,"/api/v1/categories/**").hasAnyAuthority("SCOPE_USER","SCOPE_CUSTOMERS")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/categories/**").hasAnyAuthority("SCOPE_ADMIN","SCOPE_SUPPLIERS")
                        .requestMatchers(HttpMethod.POST,"/api/v1/categories/**").hasAnyAuthority("SCOPE_ADMIN","SCOPE_SUPPLIERS")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/categories/**").hasAuthority("SCOPE_ADMIN")

                        // Products
                        .requestMatchers(HttpMethod.GET,"/api/v1/products/**").hasAnyAuthority("SCOPE_USER","SCOPE_CUSTOMERS")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/products/**").hasAnyAuthority("SCOPE_ADMIN","SCOPE_SUPPLIERS")
                        .requestMatchers(HttpMethod.POST,"/api/v1/products/**").hasAnyAuthority("SCOPE_ADMIN","SCOPE_SUPPLIERS")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/products/**").hasAuthority("SCOPE_ADMIN")

                        // Orders
                        .requestMatchers(HttpMethod.GET,"/api/v1/orders/**").hasAnyAuthority("SCOPE_USER","SCOPE_CUSTOMERS")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/orders/**").hasAnyAuthority("SCOPE_ADMIN","SCOPE_SUPPLIERS")
                        .requestMatchers(HttpMethod.POST,"/api/v1/orders/**").hasAnyAuthority("SCOPE_ADMIN","SUPPLIERS")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/orders/**").hasAuthority("SCOPE_ADMIN")

                        // Warranty
                        .requestMatchers(HttpMethod.GET,"/api/v1/warranties/**").hasAnyAuthority("SCOPE_USER","SCOPE_CUSTOMERS")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/warranties/**").hasAnyAuthority("SCOPE_ADMIN","SCOPE_SUPPLIERS")
                        .requestMatchers(HttpMethod.POST,"/api/v1/warranties/**").hasAnyAuthority("SCOPE_ADMIN","SCOPE_SUPPLIERS")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/warranties/**").hasAuthority("SCOPE_ADMIN")

                        // Bookmarks
                        .requestMatchers(HttpMethod.GET,"/api/v1/bookmarks/**").hasAnyAuthority("SCOPE_USER","SCOPE_CUSTOMERS")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/bookmarks/**").hasAnyAuthority("SCOPE_ADMIN","SCOPE_SUPPLIERS")
                        .requestMatchers(HttpMethod.POST,"/api/v1/bookmarks/**").hasAnyAuthority("SCOPE_ADMIN","SCOPE_SUPPLIERS")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/bookmarks/**").hasAuthority("SCOPE_ADMIN")

                        // Payments
                        .requestMatchers(HttpMethod.GET,"/api/v1/payment/**").hasAnyAuthority("SCOPE_USER","SCOPE_CUSTOMERS")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/payment/**").hasAnyAuthority("SCOPE_ADMIN","SCOPE_SUPPLIERS")
                        .requestMatchers(HttpMethod.POST,"/api/v1/payment/**").hasAnyAuthority("SCOPE_ADMIN","SCOPE_SUPPLIERS")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/payment/**").hasAuthority("SCOPE_ADMIN")

                .anyRequest().authenticated()
        );

        // Security Mechanism (HTTP Basic Auth)
        // HTTP Basic Auth (Username & Password)
        // http.httpBasic(Customizer.withDefaults());

        // Security Mechanism (JWT)
        http.oauth2ResourceServer(jwt -> jwt
                .jwt(jwtConfigurer -> jwtConfigurer
                        .decoder(jwtDecoder))
        );

        // Disable CSRF (Cross Site Request Forgery) Token
        http.csrf(AbstractHttpConfigurer::disable);

        // Make Stateless Session
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

    }
}