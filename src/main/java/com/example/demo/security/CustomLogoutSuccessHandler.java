package com.example.demo.security;

import com.example.demo.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.stereotype.*;

import java.io.*;

// MVC 방식은 로그아웃에 성공하면 루트페이지로 "이동"한다
// REST 방식은 "백에서 redirect한다"는 개념이 존재할 수 없다 -> 로그아웃이 "성공"했으므로 200으로 응답
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    ResponseUtil.sendJsonResponse(response, 200, "로그아웃 성공");
  }
}
