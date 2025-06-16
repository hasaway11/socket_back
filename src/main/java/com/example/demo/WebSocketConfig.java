package com.example.demo;

import org.springframework.context.annotation.*;
import org.springframework.messaging.simp.config.*;
import org.springframework.web.socket.config.annotation.*;

// HTTP는 요청과 응답으로 구성 : 비연결형(connectionless), half-duplex
// - 전통적인 요청 방식은 서버가 새로운 HTML 페이지로 응답(전체 페이지 리로딩 or 화면이동)
// - AJAX가 가져온 개선
//   백그라운드에서 HTTP 요청을 보내고, 응답을 받아서 페이지를 새로 고침하지 않고 내용을 업데이트

// 웹소켓
// - 요청-응답이 아닌 연결을 유지(connection oriented)하면서 full-duplex 양방향 통신을 제공
// - 실시간성이 높아 채팅, 실시간 알림 등에 적합

// 웹소켓은 웹 기반 양방향 통신 프로토콜로 어떤 언어로도 구현 가능
// 프론트엔드 환경에서는 자바스크립트가 사실상 표준 언어이기때문에, 웹소켓 클라이언트 구현의 대부분은 자바스크립트 기반

// 웹소켓은 양방향 통신 원조이자 표준
// SockJS는 호환성, 네트워크 환경 대응에 융통성 있는 복제품
// 스프링 서버는 그 둘을 같은 방식으로 받아들일 수 있도록 추상화 (했지만 실제 스프링 예제들도 SockJS 기반)
// stomp는 웹소켓 기반으로 양방향 메시징 서비스를 제공(발행과 구독의 개념을 지원)

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // stomp 접속 주소 url = ws://localhost:8080/ws
    // 모든 도메인에서 접속을 허용(CORS 정책 관련)
    // WebSocket 또는 SockJS로 접속을 허용
    registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    // 클라이언트가 이 접두사로 시작하는 경로를 구독(subscribe) 하면 메시지를 수신
    // 예: 클라이언트가 /sub/chat/room/1을 구독 → 서버가 이 경로로 메시지 보내면 수신.
    registry.enableSimpleBroker("/sub", "/queue");

    // 클라이언트가 서버에 메시지를 보낼 때 (send) 사용하는 경로 접두사
    // 예: 클라이언트가 /pub/chat/message로 메시지 전송 → 해당 경로로 맵핑된 컨트롤러 메서드가 실행됨
    registry.setApplicationDestinationPrefixes("/pub");
  }
}



