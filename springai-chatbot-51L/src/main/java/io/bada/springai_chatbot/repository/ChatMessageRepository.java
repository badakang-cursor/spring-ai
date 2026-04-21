package io.bada.springai_chatbot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.bada.springai_chatbot.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{
    List<ChatMessage> findByUserIdOrderByCreatedAtAsc(String userId);
}
