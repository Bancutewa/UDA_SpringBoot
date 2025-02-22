package com.example.demo_spring;

import com.example.demo_spring.service.UserDetailsServiceIml;
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
        return new ProviderManager(provider);
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF nếu dùng API REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/register", "/login").permitAll()
                        .requestMatchers("/home").authenticated() // Phải đăng nhập mới truy cập được home
                        .requestMatchers("/userInfo/**").hasAnyRole("USER", "ADMIN") // Chỉ USER hoặc ADMIN mới truy cập
                        .requestMatchers("/addUser", "/editUser/**", "/deleteUser/**", "/companies/**", "/addCompany/**", "/companyUsers/**","/selectUsersForCompany/**" ).hasAnyRole( "ADMIN") // Chỉ USER hoặc ADMIN mới truy cập
                        .anyRequest().authenticated() // Các API khác cần đăng nhập
                )
                .formLogin(form -> form
                        .loginPage("/login") // Trang đăng nhập
                        .loginProcessingUrl("/login") // Xử lý POST từ form
                        .usernameParameter("email") // Cấu hình lấy username từ `email`
                        .passwordParameter("password") // Cấu hình lấy password từ `password`
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}
