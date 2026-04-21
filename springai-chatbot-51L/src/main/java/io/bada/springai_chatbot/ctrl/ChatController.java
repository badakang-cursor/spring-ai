package io.bada.springai_chatbot.ctrl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.bada.springai_chatbot.dto.ChatRequestDto;
import io.bada.springai_chatbot.dto.ChatResponseDto;
import io.bada.springai_chatbot.service.CustomerSupportService;



@RestController
public class ChatController {
    private final CustomerSupportService service;

    public ChatController(CustomerSupportService service){
        this.service = service;

    }

    @PostMapping("/chat")
    public ChatResponseDto chat(@RequestBody ChatRequestDto request){
        System.out.println("요청된 request 값 : "+ request);
        ChatResponseDto responseMessage = service.getChatResponse(request.getUserId(), request.getMessage());

        return responseMessage;

    }
}
