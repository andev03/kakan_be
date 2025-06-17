package com.kakan.admission_advisor_service.repository;

import com.kakan.admission_advisor_service.pojo.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {

    List<ChatMessage> findBySenderIdAndSessionId(Integer senderId, UUID sessionId);
}
