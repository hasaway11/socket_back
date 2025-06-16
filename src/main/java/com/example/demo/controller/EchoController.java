package com.example.demo.controller;

import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;
import java.time.*;


@Slf4j
@Controller
public class EchoController {
  // 스프링이 STOMP를 단순화환 메시징 기능을 위해 제공하는 템플릿
  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  // "/pub/echo" 메시지 요청에 반응 → /sub/chat 구독자에게 전송
  @MessageMapping("/echo1")
  public void hello() {
    messagingTemplate.convertAndSend("/sub/echo1",  "hello js");
  }

  // echo : 요청 메시지를 그대로 모든 구독자에게 broadcast한다
  @MessageMapping("/echo2")
  public void echo2(String message) {
    messagingTemplate.convertAndSend("/sub/echo2", message);
  }

  // 서버에서 10초마다 모든 구독자에게 broadcast
  @Scheduled(fixedDelay=10000)
  public void scheduled1() {
    LocalDateTime now = LocalDateTime.now();
    messagingTemplate.convertAndSend("/sub/noti", now);
  }

  // 서버에서 매분마다 모든 구독자에게 broadcast
  @Scheduled(cron = "0 0/1 * 1/1 * ?")
  public void scheduled2() {
    messagingTemplate.convertAndSend("/sub/noti", "정각입니다");
  }

  @MessageMapping("/echo3")
  public void echo3(String message, Principal principal) {
    String username = principal==null? "GUEST" : principal.getName();
    System.out.println(username);
    messagingTemplate.convertAndSend("/sub/echo3",  username + ":" + message);
  }

  @PostMapping("/api/messages")
  public ResponseEntity<Void> echo4(String receiver, String text, Principal principal) {
    String sender = principal==null? "GUEST" : principal.getName();
    if(receiver.equals(sender))
      return ResponseEntity.status(409).body(null);
    System.out.println(receiver);
    messagingTemplate.convertAndSendToUser(receiver, "/sub/echo4",  sender+" 메시지 전송");
    return ResponseEntity.ok(null);
  }
}