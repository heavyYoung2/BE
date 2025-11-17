package hongik.heavyYoung.domain.member.controller;

import hongik.heavyYoung.domain.member.dto.MemberResponseDTO;
import hongik.heavyYoung.domain.member.service.MemberQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.security.auth.AuthMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member API - 학생", description = "학생 - 블랙리스트 확인 API")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberQueryService memberQueryService;

    @PreAuthorize("hasRole(\"USER\")")
    @Operation(summary = "내 블랙리스트 여부 조회")
    @GetMapping("/blacklist/me")
    public ApiResponse<MemberResponseDTO.BlacklistInfo> getBlacklist (
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        return ApiResponse.onSuccess(memberQueryService.getBlacklist(authMemberId));
    }
}
