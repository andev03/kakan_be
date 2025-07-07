package com.kakan.admission_advisor_service.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chat_message")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue
    UUID id;

    @Column(name = "sender_type", nullable = false)
    String senderType;

    @Column(name = "sender_id")
    Integer senderId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    String content;

    @Builder.Default
    @Column(name = "sent_at")
    LocalDateTime sentAt = LocalDateTime.now();

    @Column(name = "deleted_at")
    LocalDateTime deletedAt;
}
