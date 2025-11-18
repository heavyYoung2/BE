package hongik.heavyYoung.domain.chat.service.impl;

import hongik.heavyYoung.domain.chat.converter.ChatConverter;
import hongik.heavyYoung.domain.chat.dto.ChatRequestDTO;
import hongik.heavyYoung.domain.chat.dto.ChatResponseDTO;
import hongik.heavyYoung.domain.chat.service.ChatCommandService;
import hongik.heavyYoung.global.ai.AIClient;
import hongik.heavyYoung.global.ai.PromptFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatCommandServiceImpl implements ChatCommandService {

    private final AIClient aiClient;
    private final ChatMemory chatMemory;

    @Override
    public ChatResponseDTO.Response getChatResponse(Long authMemberId, ChatRequestDTO.Request request) {

        // 채팅 히스토리 가져오기
        List<Message> chatHistory = new ArrayList<>(chatMemory.get(String.valueOf(authMemberId)));

        // 사용자 메시지 추가
        chatHistory.add(new UserMessage(request.getContent()));

        // 프롬프트 생성
        Prompt prompt = PromptFactory.buildPrompt(chatHistory);

        // AI 클라이언트를 통해 응답 받기
        String response = aiClient.getResponse(prompt);

        // 대화 히스토리에 사용자 메시지와 어시스턴트 메시지 추가
        chatMemory.add(String.valueOf(authMemberId), new UserMessage(request.getContent()));
        chatMemory.add(String.valueOf(authMemberId), new AssistantMessage(response));

        return ChatConverter.toResponseDTO(response);
    }
}
