package hongik.heavyYoung.domain.chat.service;

import hongik.heavyYoung.domain.chat.dto.ChatRequestDTO;
import hongik.heavyYoung.domain.chat.dto.ChatResponseDTO;

public interface ChatCommandService {
    ChatResponseDTO.Response getChatResponse(Long authMemberId, ChatRequestDTO.Request request);
}
