package net.softbell.bsh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer
{
    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages)
    {
//        messages.simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.DISCONNECT, SimpMessageType.UNSUBSCRIBE).permitAll()
//        		.simpSubscribeDestMatchers("/**").authenticated()
//        		.simpDestMatchers("/**").authenticated()
//    			.anyMessage().authenticated();
    }
    
    @Override
    protected boolean sameOriginDisabled()
    {
        return true;
    }
}