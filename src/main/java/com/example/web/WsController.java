package com.example.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.domain.Message;
import com.example.domain.WiselyMessage;
import com.example.domain.WiselyResponse;

@Controller
public class WsController {

	@Autowired
	private SimpMessagingTemplate messageTemplate;
	
	/**
	 * 广播式
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@MessageMapping("/welcome") // 映射请求地址
	@SendTo("/topic/getResponse") // 当服务器有消息时，会对订阅了@SendTo中的路径的浏览器发送消息
	public WiselyResponse say(WiselyMessage message) throws Exception {
		Thread.sleep(3000);
		return new WiselyResponse("Welcome, " + message.getName() + "!");
	}
	
	/**
	 * 点对点式
	 * 在springmvc 中可以直接获得principal,principal 中包含当前用户的信息
	 * @param principal
	 * @param message
	 */
	@MessageMapping("/chat")
	public void handleChat(Principal principal, Message message) {
		 /**
         * 此处是一段硬编码。如果发送人是wyf 则发送给 wisely 如果发送人是wisely 就发送给 wyf。
         */
		if(principal.getName().equals("xiawq")) {
			//通过convertAndSendToUser 向用户发送信息,
            // 第一个参数是接收消息的用户,第二个参数是浏览器订阅的地址,第三个参数是消息本身
			messageTemplate.convertAndSendToUser("panq", "/queue/notifications",
					principal.getName()+"-send:"+message.getName());
		}else{
			messageTemplate.convertAndSendToUser("xiawq", "/queue/notifications",
					principal.getName()+"-send:"+message.getName());
		}
	}
}
