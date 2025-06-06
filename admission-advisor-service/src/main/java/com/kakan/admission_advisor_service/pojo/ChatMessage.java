package com.kakan.admission_advisor_service.pojo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chat_message")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessage {

    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    ChatSession session;

    @Column(name = "sender_type", nullable = false)
    String senderType;

    @Column(name = "sender_id")
    UUID senderId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    String content;

    @Column(name = "sent_at")
    LocalDateTime sentAt = LocalDateTime.now();

    @Column(name = "deleted_at")
    LocalDateTime deletedAt;
}
