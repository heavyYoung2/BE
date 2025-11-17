package hongik.heavyYoung.domain.chat.controller;

import hongik.heavyYoung.domain.chat.dto.ChatResponseDTO;
import hongik.heavyYoung.domain.chat.service.ChatCommandService;
import hongik.heavyYoung.domain.chat.dto.ChatRequestDTO;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.security.auth.AuthMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Chatbot API - 학생", description = "학생 - 챗봇 API")
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatCommandService chatCommandService;

    @PreAuthorize("hasRole(\"USER\")")
    @Operation(summary = "챗봇 응답 받기")
    @PostMapping
    public ApiResponse<ChatResponseDTO.Response> getChatResponse(
            @RequestBody ChatRequestDTO.Request request,
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        return ApiResponse.onSuccess(chatCommandService.getChatResponse(authMemberId, request));
    }
}
