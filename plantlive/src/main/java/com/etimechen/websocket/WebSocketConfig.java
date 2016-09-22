package com.etimechen.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration  
@EnableWebSocket 
public class WebSocketConfig implements WebSocketConfigurer {
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addHandler(handler(), "/websocket").addInterceptors(handshakeInterceptor()).setAllowedOrigins("*");  
        registry.addHandler(handler(), "/sockjs/websocket").addInterceptors(handshakeInterceptor()).withSockJS(); 
	}
	
	@Bean  
    public MyWebSocketHandle handler() {  
        return new MyWebSocketHandle();  
    }  
  
    @Bean  
    public HandshakeInterceptor handshakeInterceptor() {  
        return new HandshakeInterceptor();  
    } 
}
