package hongik.heavyYoung.domain.chat.converter;

import hongik.heavyYoung.domain.chat.dto.ChatResponseDTO;

public class ChatConverter {
    public static ChatResponseDTO.Response toResponseDTO(String response) {
        return ChatResponseDTO.Response.builder()
                .content(response)
                .build();
    }
}
