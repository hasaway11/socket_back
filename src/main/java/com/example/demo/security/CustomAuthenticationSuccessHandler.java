package com.example.demo.security;

import com.example.demo.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    // 아이디와 권한을 응답한다
    String username = authentication.getName();
    String role = authentication.getAuthorities().stream().map(a->a.getAuthority()).findFirst().orElse("");
    Map<String, String> responseBody = Map.of("username", username,"role", role);
    ResponseUtil.sendJsonResponse(response, 200, responseBody);
  }
}








