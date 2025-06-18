package com.kakan.admission_advisor_service.service.impl;

import com.kakan.admission_advisor_service.dto.ChatMessageDto;
import com.kakan.admission_advisor_service.dto.request.ChatMessageRequestDto;
import com.kakan.admission_advisor_service.dto.response.SendChatResponseDto;
import com.kakan.admission_advisor_service.enums.Sender;
import com.kakan.admission_advisor_service.mapper.ChatMessageMapper;
import com.kakan.admission_advisor_service.mapper.ChatSessionMapper;
import com.kakan.admission_advisor_service.pojo.ChatMessage;
import com.kakan.admission_advisor_service.pojo.ChatSession;
import com.kakan.admission_advisor_service.repository.ChatMessageRepository;
import com.kakan.admission_advisor_service.repository.ChatSessionRepository;
import com.kakan.admission_advisor_service.service.ChatMessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    @Value("${gemini.api.url}")
    String geminiApiUrl;

    @Value("${gemini.api.key}")
    String geminiApiKey;

    final ChatSessionMapper chatSessionMapper;

    final ChatMessageMapper chatMessageMapper;

    final ChatMessageRepository chatMessageRepository;

    final ChatSessionRepository chatSessionRepository;

    @Override
    public List<ChatMessageDto> getAllChatMessageByAccountIdAndSessionId(Integer accountId, UUID sessionId) {
        return chatMessageMapper.toDtoList(chatMessageRepository.findBySenderIdAndSessionId(accountId, sessionId));
    }

    @Override
    @Transactional
    public SendChatResponseDto chatMessageWithAdmission(ChatMessageRequestDto chatMessageRequestDto) {
        ChatSession chatSession = validateChatSession(chatMessageRequestDto);

        ChatMessage chatMessage =
                chatMessageRepository.save(
                        ChatMessage.builder()
                                .content(chatMessageRequestDto.getMessage())
                                .deletedAt(null)
                                .senderId(chatMessageRequestDto.getSenderId())
                                .senderType(Sender.USER.toString().toLowerCase())
                                .session(chatSession)
                                .build()
                );

        return SendChatResponseDto.builder()
                .chatUserDto(chatMessageMapper.toDto(chatMessage))
                .chatSessionDto(chatSessionMapper.toDto(chatSession))
                .chatAI(chatMessageMapper.toDto(saveChatAi(chatMessageRequestDto, chatMessage)))
                .build();
    }

    @Transactional
    private ChatSession validateChatSession(ChatMessageRequestDto chatMessageRequestDto) {
        ChatSession chatSession = chatSessionRepository.findById(chatMessageRequestDto.getSessionId()).orElse(null);

        if (chatSession == null) {
            return chatSessionRepository.save(
                    ChatSession.builder()
                            .title(chatMessageRequestDto.getMessage())
                            .accountId(chatMessageRequestDto.getSenderId())
                            .build()
            );
        }
        return chatSession;
    }

    @Transactional
    private ChatMessage saveChatAi(ChatMessageRequestDto chatMessageRequestDto, ChatMessage chatMessage){
        String messageResponseAi = getMessageFromAdmission(chatMessageRequestDto);

        return chatMessageRepository.save(
                ChatMessage.builder()
                        .session(chatMessage.getSession())
                        .content(messageResponseAi)
                        .senderType(Sender.BOT.toString().toLowerCase())
                        .build()
        );
    }
    private String getMessageFromAdmission(ChatMessageRequestDto chatMessageRequestDto) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", chatMessageRequestDto.getMessage())
                        })
                }
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                geminiApiUrl + geminiApiKey,
                requestEntity,
                String.class
        );

        return response.getBody();
    }
}
