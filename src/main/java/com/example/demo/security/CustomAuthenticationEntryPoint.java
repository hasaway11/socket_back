package com.example.demo.security;

import com.example.demo.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.*;
import org.springframework.security.web.*;
import org.springframework.stereotype.*;

import java.io.*;

// 401을 처리
// MVC 방식에서는 401이 발생하면 로그인 페이지로 redirect시키지만, REST에서는 401로 인증 오류라는 사실을 알려준다
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    ResponseUtil.sendJsonResponse(response, 401, "로그인이 필요합니다");
  }
}
