package com.example.demo.controller;

import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;
import java.util.*;

@RequiredArgsConstructor
@Controller
public class AuthController {
  @GetMapping(path = "/api/auth/check")
  public ResponseEntity<Map<String, String>> checkLogin(Authentication authentication, HttpSession session) {
    // 사용자 인증 정보는 Authentication 인터페이스로 접근할 수 있다
    if (authentication != null && authentication.isAuthenticated()) {
      String username = authentication.getName();

      // GrantedAuthority는 사용자의 권한을 나타내는 인터페이스
      // 사용자가 권한을 1개씩만 가진다는 가정 아래 사용자의 첫번째 GrantedAuthority의 이름을 꺼낸다
      String role = authentication.getAuthorities().stream().map(a->a.getAuthority()).findFirst().orElse("");

      // 사용자의 아이디와 권한을 응답 -> 프론트에서 로그인 여부에 더해 권한에 따라 접근제어(access control)
      Map<String, String> response = Map.of("username", username,"role", role);
      return ResponseEntity.ok(response);
    }
    return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
  }
}
