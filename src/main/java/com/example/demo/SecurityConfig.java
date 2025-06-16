package com.example.demo;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.provisioning.*;
import org.springframework.security.web.*;
import org.springframework.security.web.access.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.web.cors.*;

import java.util.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private AuthenticationEntryPoint authenticationEntryPoint;
  @Autowired
  private AuthenticationSuccessHandler authenticationSuccessHandler;
  @Autowired
  private AuthenticationFailureHandler authenticationFailureHandler;
  @Autowired
  private AccessDeniedHandler accessDeniedHandler;
  @Autowired
  private LogoutSuccessHandler logoutSuccessHandler;

  @Bean
  public UserDetailsService users() {
    UserDetails user1 = User.builder().username("spring").password(passwordEncoder.encode("1234")).roles("USER").build();
    UserDetails user2 = User.builder().username("summer").password(passwordEncoder.encode("1234")).roles("USER").build();
    UserDetails user3 = User.builder().username("winter").password(passwordEncoder.encode("1234")).roles("USER").build();
    return new InMemoryUserDetailsManager(user1, user2, user3);
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity config) throws Exception {
    // CORS 설정
    config.cors(cors->cors.configurationSource(corsConfigurationSource()));
    // 페이지 위조를 막기위한 CSRF기능을 끈다
    // 페이지를 보낼 때 CSRF 토큰을 추가해서 보낸 다음 submit할때 토큰을 돌려받아 서버가 보낸 페이지라는 것을 확인 -> REST에서는 필요없다
    config.csrf(csrf-> csrf.disable());
    config.formLogin(form->form.loginPage("/login").loginProcessingUrl("/login")
        .successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler));
    config.logout(logout-> logout.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler));
    config.exceptionHandling(handler->
        handler.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint));
    return config.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOriginPatterns(Arrays.asList("*"));
    config.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE"));
    config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
    src.registerCorsConfiguration("/**", config);
    return src;
  }
}
