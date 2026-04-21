package io.bada.springai_chatbot_rag.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

	@Autowired
	private ChatModel chatModel;

	private final String systemPrompt = """
			당신은 "Awesome Tech"라는 회사의 E-커머스 고객 지원 챗봇입니다.
			항상 친절하고 명확하게 답변해야 합니다. 사용자가 상품에 대해 물으면 아래 정보를 기반으로 답해주세요.

			- 상품명: 갤럭시 AI 북
			- 가격: 1,500,000원
			- 특징: 최신 AI 기능이 탑재된 고성능 노트북. 가볍고 배터리가 오래갑니다.
			- 재고: 현재 구매 가능

			- 상품명: AI 스마트 워치
			- 가격: 350,000원
			- 특징: 건강 모니터링과 스마트폰 연동 기능 제공. 방수 기능 포함.
			- 재고: 품절 (5일 후 재입고 예정)

			내부에 없는 정보일 경우, 정중히 안내하면서도 일반적인 정보나 유사한 내용을 최대한 제공해주세요.

			""";

    // ChatController(springaiChatbotRagApplication springAiChatbotRagApplication) {
    //     this.springaiChatbotRagApplication = springaiChatbotRagApplication;
    // }

	//RAG 없이 바로 OpenAI에 질문
	@PostMapping("/simple")
	public String simpleChat(@RequestBody Map<String, Object> playload) {
		String question = (String)playload.get("question");
		System.out.println("요청받은 질문 :" + question);
		
		// 시스템 메시비와 사용자 메시지
		List<Message> messages = new ArrayList<Message>();
		messages.add(new SystemPromptTemplate(systemPrompt).createMessage());
		messages.add(new UserMessage(question));
		
		//ChatModel 요청
		Prompt prompt = new Prompt(messages);
		ChatResponse response = chatModel.call(prompt);
		
		return response.getResult().getOutput().getText();
		
	}


}












