package net.softbell.bsh.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 웹소켓 보안 설정
 */
@Configuration
class WebSocketSecurityConfig : AbstractSecurityWebSocketMessageBrokerConfigurer() {
    override fun configureInbound(messages: MessageSecurityMetadataSourceRegistry) {
//        messages.simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.DISCONNECT, SimpMessageType.UNSUBSCRIBE).permitAll()
//        		.simpSubscribeDestMatchers("/**").authenticated()
//        		.simpDestMatchers("/**").authenticated()
//    			.anyMessage().authenticated();
    }

    override fun sameOriginDisabled(): Boolean {
        return true
    }
}