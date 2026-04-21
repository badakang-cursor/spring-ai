package io.bada.springai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import io.bada.springai.service.OllamaChatService;

@SpringBootApplication
public class SpringaiApplication {

	public static void main(String[] args) {
		
		//	ConfigurableApplicationContext context = SpringApplication.run(SpringaiApplication.class, args);
		// 	ChatService chatService = context.getBean(ChatService.class);
		// 	chatService.run();

		// 3강
		ConfigurableApplicationContext context = SpringApplication.run(SpringaiApplication.class, args);
		OllamaChatService chatService = context.getBean(OllamaChatService.class);
		String response = chatService.getQuestion();

		System.out.println("결과 : ");
		System.out.println(response);

	}
}
