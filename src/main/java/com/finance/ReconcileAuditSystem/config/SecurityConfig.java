package com.finance.ReconcileAuditSystem.config;

import com.finance.ReconcileAuditSystem.service.UserDetailServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserDetailServiceImpl userDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
         http.authorizeHttpRequests(auth -> auth
                         .requestMatchers("/auth/test", "/auth/signout").permitAll()
                         .requestMatchers(HttpMethod.POST,"/auth/signin").permitAll()
                         .requestMatchers("/signup/test", "/signup/up").permitAll()
                         .anyRequest().authenticated())
                 .formLogin(AbstractHttpConfigurer::disable)
                 .httpBasic(AbstractHttpConfigurer::disable)
                 .csrf(AbstractHttpConfigurer::disable)
                 .cors(cors -> cors.configurationSource(request -> {
                     CorsConfiguration corsConfig = new CorsConfiguration();
                     corsConfig.setAllowedOrigins(List.of("http://localhost:3000"));
                     corsConfig.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
                     corsConfig.setAllowedHeaders(List.of("*"));
                     corsConfig.setAllowCredentials(true);
                     return corsConfig;
                 }));
         http.logout(logout-> logout
                 .logoutUrl("/auth/signout")
                 .invalidateHttpSession(true)
                 .clearAuthentication(true)
                 .deleteCookies("JSESSIONID")
                 .logoutSuccessHandler((request,response, authentication)->{
                     response.setStatus(HttpServletResponse.SC_OK);
                 }));

         http.sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

         return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
