package com.kakan.admission_advisor_service.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chat_session")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatSession {
    @Id
    @GeneratedValue
    UUID id;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "account_id", nullable = false)
    Integer accountId;

    @Builder.Default
    @Column(name = "started_at")
    LocalDateTime startedAt = LocalDateTime.now();

    @Builder.Default
    @Column(name = "status")
    String status = "active";

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ChatMessage> messages;
}
