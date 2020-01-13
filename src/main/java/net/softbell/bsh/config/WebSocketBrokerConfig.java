package net.softbell.bsh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 웹 소켓 설정
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/api/stomp/topic", "/api/stomp/queue", "/api/stomp/pub");
		registry.setApplicationDestinationPrefixes("/api/stomp/topic", "/api/stomp/queue", "/api/stomp/pub");
		//registry.setUserDestinationPrefix("");
//		registry.setApplicationDestinationPrefixes("/");
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws")
				.setAllowedOrigins("*")
				.withSockJS();
				/*.setHandshakeHandler(new DefaultHandshakeHandler() {
					 
				      public boolean beforeHandshake(
				        ServerHttpRequest request, 
				        ServerHttpResponse response, 
				        WebSocketHandler wsHandler,
				        Map attributes) throws Exception {
				  
				            if (request instanceof ServletServerHttpRequest) {
				                ServletServerHttpRequest servletRequest
				                 = (ServletServerHttpRequest) request;
				                HttpSession session = servletRequest
				                  .getServletRequest().getSession();
				                attributes.put("sessionId", session.getId());
				            }
				                return true;
				        }});*/
	}
}