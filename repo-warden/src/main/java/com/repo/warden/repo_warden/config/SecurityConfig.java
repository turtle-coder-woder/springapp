package com.repo.warden.repo_warden.config;

import com.repo.warden.repo_warden.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF if not using in Thymeleaf forms
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/signup", "/login", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login-me")
                        .loginProcessingUrl("/authenticate")
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/");
                        })
                        .failureHandler((request, response, exception) -> {
                            response.sendRedirect("/signup");
                        })
                        .successForwardUrl("/")
                        .defaultSuccessUrl("/")
                        .usernameParameter("email")  // Tell Spring Security to use "email" instead of "username"
                        .passwordParameter("password")

                        .permitAll()

                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/signupme")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("remember-me")
                        .tokenValiditySeconds(86400)
                        .userDetailsService(userDetailsService)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .expiredUrl("/login?expired")
                );

        return http.build();
    }

}
