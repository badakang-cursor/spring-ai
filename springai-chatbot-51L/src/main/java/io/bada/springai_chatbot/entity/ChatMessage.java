package io.bada.springai_chatbot.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String content;
    private boolean isUser;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ChatMessage(String userId, String content, boolean isUser){
        super();
        this.userId = userId;
        this.content = content;
        this.isUser = isUser;
        this.createdAt = LocalDateTime.now();
    }
}
