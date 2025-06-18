package com.example.demo.controller;

import lombok.extern.slf4j.*;
import org.springframework.context.event.*;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.stereotype.*;
import org.springframework.web.socket.messaging.*;

@Slf4j
@Component
public class WebSocketEventListener {

  // 연결될 때 호출
  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    log.info("🔌 웹소켓 연결됨: {}", event);
  }

  // 연결 해제될 때 호출
  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    log.info("❌ 웹소켓 연결 해제됨: 세션 ID = {}", headerAccessor.getSessionId());
  }
}
