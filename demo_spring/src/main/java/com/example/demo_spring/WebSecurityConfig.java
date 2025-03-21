package com.example.demo_spring;

import com.example.demo_spring.service.UserDetailsServiceIml;
import com.example.demo_spring.JwtAuthenticationFilter;
import com.example.demo_spring.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceIml();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(List.of(provider));
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // Chấp nhận tất cả nguồn
        configuration.addAllowedMethod("*"); // Chấp nhận tất cả phương thức (GET, POST, PUT, DELETE,...)
        configuration.addAllowedHeader("*"); // Chấp nhận tất cả headers
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Kích hoạt CORS
                .csrf(csrf -> csrf.disable()) // Tắt CSRF cho API RESTful
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/users/register", "/api/v1/users/login", "/api/v1/students/register").permitAll() // Cho phép đăng ký và đăng nhập
                        .requestMatchers("/api/v1/students", "/api/v1/students/{id}").hasAnyRole("ADMIN", "USER") // Cho phép ADMIN và USER truy cập
                        .requestMatchers("/api/v1/students/**").hasRole("ADMIN") // Chỉ ADMIN có quyền truy cập vào các endpoint khác của sinh viên
                        .requestMatchers("/api/v1/users/**").hasRole("ADMIN") // Chỉ ADMIN có quyền truy cập vào các endpoint của người dùng
                        .requestMatchers("/api/v1/companies/**").hasRole("ADMIN") // Chỉ ADMIN có quyền truy cập vào các endpoint của công ty
                        .anyRequest().authenticated() // Tất cả các yêu cầu khác đều cần xác thực
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
