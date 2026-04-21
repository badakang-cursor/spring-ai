package io.bada.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OllamaChatService {

    private final ChatClient chatClient;

    public OllamaChatService(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }
    

    public String getQuestion(){
        return chatClient.prompt("한국의 위인 한 명만 알려죠").call().content();
    }
    
}
