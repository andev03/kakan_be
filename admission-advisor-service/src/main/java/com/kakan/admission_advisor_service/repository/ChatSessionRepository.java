package com.kakan.admission_advisor_service.repository;

import com.kakan.admission_advisor_service.pojo.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatSessionRepository extends JpaRepository<ChatSession, UUID> {

    List<ChatSession> findByAccountId(Integer accountId);
}
