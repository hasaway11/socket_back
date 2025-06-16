package com.example.demo.util;

import com.fasterxml.jackson.databind.*;
import jakarta.servlet.http.*;

import java.io.*;


public class ResponseUtil {
  private static final ObjectMapper mapper = new ObjectMapper();

  // Jackson 라이브러리를 이용해 객체를 JSON 문자열로 변경하는 정적 메소드
  public static void sendJsonResponse(HttpServletResponse res, int statusCode, Object body) throws IOException {
    res.setStatus(statusCode);
    res.setContentType("application/json; charset=UTF-8");
    res.getWriter().write(mapper.writeValueAsString(body));
  }
}
