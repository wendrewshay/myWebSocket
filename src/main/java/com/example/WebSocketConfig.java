package com.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker // 开启使用STOMP协议来传输基于代理(message broker)的消息，这时控制器支持使用@MessageMapping
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{

	@Override
	public void registerStompEndpoints(StompEndpointRegistry arg0) {//注册STOMP协议的节点，并映射指定的URL
		arg0.addEndpoint("/endpointWisely").withSockJS();//注册一个使用STOMP协议的endpoint，并指定使用SockJS协议
		arg0.addEndpoint("/endpointChat").withSockJS();//注册一个名为endpointChat的endpoint
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {//配置消息代理(Message Broker)
		registry.enableSimpleBroker("/queue", "/topic");//广播式配置一个/topic消息代理，点对点式配置一个/queue消息代理
	}

}
