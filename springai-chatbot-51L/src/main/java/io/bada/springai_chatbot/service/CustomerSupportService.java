package io.bada.springai_chatbot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.bada.springai_chatbot.dto.ChatMessageDto;
import io.bada.springai_chatbot.dto.ChatResponseDto;
import io.bada.springai_chatbot.entity.ChatMessage;
import io.bada.springai_chatbot.repository.ChatMessageRepository;

@Service
public class CustomerSupportService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    // ChatClient를 통한 AI 모델 객체 선언
    private final ChatClient chatClient;

    public CustomerSupportService(ChatClient.Builder chatBuilder){
        this.chatClient = chatBuilder.build();
    }

    // 챗봇의 역할을 정의하는 시스템 프롬프트
    private final String systemPrompt = """
            당신은 "Awesome Tech"라는 회사의 E-커머스 고객 지원 챗봇 입니다.
            항상 친절하고 명확하게 답변해야 합니다. 사용자가 상품에 대해서 물으면 아래 정보를 기반으로 답해주세요.

            - 상품명 : 갤럭시 AI 북
            - 가격 : 1,500,000원
            - 특징 : 최신 AI 기능이 탑재된 고성능 노트복, 가볍고 배터리가 오래간다.
            - 재고 : 현재 구매 가능

            - 상품명 : AI 스마트 워치
            - 가격 : 350,000원
            - 특징 : 건강 모니터링이 가능하고, 스마트폰과 연동 기능을 제공, 방수 기능을 포함
            - 재고 : 품절(5일 후 재입고 예정)

            내부에 없는 정보일 경우, 정중히 "죄송합니다. 제품이 없습니다."를 답변해주세요.
            """;

            // 내부에 없는 정보일 경우, 정중히 안내하면서도 일반적인 정보가 유사한 내용을 최대한 제공해 주세요.

    public ChatResponseDto getChatResponse(String userId, String userMessage){
        List<ChatMessage> previousMessages = chatMessageRepository.findByUserIdOrderByCreatedAtAsc(userId);

        List<Message> history = previousMessages.stream()
                                    .map(cm -> cm.isUser()
                                                ? new UserMessage(cm.getContent())
                                                : new AssistantMessage(cm.getContent())
                                        )
                                        .collect(Collectors.toList());

        // 시스템 메시지와 사용자 메시지를 포함한 전체 대화 생성
        List<Message> conversion = new ArrayList<Message>();
        conversion.add(new SystemPromptTemplate(systemPrompt).createMessage());
        conversion.addAll(history);
        conversion.add(new UserMessage(userMessage));

        //ChatClient를 사용하여 AI 모델 요청
        Prompt prompt = new Prompt(conversion);
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
       
        // Json  구조로 변경 하여 리스트로 생성
        List<ChatMessageDto> messages = new ArrayList<ChatMessageDto>();

        for (ChatMessage msg : previousMessages) {
            String sender = msg.isUser() ? "user":"ai";
            String content = msg.getContent();

            messages.add(new ChatMessageDto(sender, content));
        }

        messages.add(new ChatMessageDto("user", userMessage));

        String aiResponseMessage = "AI 응답이 유효하지 않습니다.";
        //Null 체크
        if(response == null || response.getResult() == null || response.getResult().getOutput() == null){
            System.err.println(aiResponseMessage);
            messages.add(new ChatMessageDto("ai", aiResponseMessage));
        }else{
            aiResponseMessage = response.getResult().getOutput().getText();
            messages.add(new ChatMessageDto("ai", aiResponseMessage));
        }

        // DB 저장
        chatMessageRepository.save(new ChatMessage(userId, userMessage, true));
        chatMessageRepository.save(new ChatMessage(userId, aiResponseMessage, false));

        return new ChatResponseDto(messages);
    }
}
