package hongik.heavyYoung.domain.member.controller.admin;

import hongik.heavyYoung.domain.member.dto.MemberResponseDTO;
import hongik.heavyYoung.domain.member.dto.authDTO.AuthRequestDTO;
import hongik.heavyYoung.domain.member.dto.authDTO.AuthResponseDTO;
import hongik.heavyYoung.domain.member.service.general.MemberCommandService;
import hongik.heavyYoung.domain.member.service.general.MemberQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.security.auth.AuthMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member API - 학생", description = "학생 - 블랙리스트 확인 API")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    @PreAuthorize("hasRole(\"USER\")")
    @Operation(summary = "내 블랙리스트 여부 조회")
    @GetMapping("/blacklist/me")
    public ApiResponse<MemberResponseDTO.BlacklistInfo> getBlacklist (
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        return ApiResponse.onSuccess(memberQueryService.getBlacklist(authMemberId));
    }

    @PreAuthorize("hasRole(\"USER\")")
    @Operation(summary = "마이페이지 조회")
    @GetMapping("/me")
    public ApiResponse<MemberResponseDTO.MyPageInfo> getMyPage(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        return ApiResponse.onSuccess(memberQueryService.findMyPage(authMemberId));
    }

    @PreAuthorize("hasRole(\"USER\")")
    @Operation(summary = "비밀번호 변경",
            description = "기존 비밀번호, 새 비밀번호, 새 비밀번호 확인을 통해 비밀번호를 변경합니다.")
    @PostMapping("/change-password")
    public ApiResponse<AuthResponseDTO.ChangePasswordResponseDTO> changePassword(@Parameter(hidden = true) @AuthMemberId Long authMemberId,
                                                                                 @RequestBody @Valid AuthRequestDTO.ChangePasswordRequestDTO dto) {
        AuthResponseDTO.ChangePasswordResponseDTO response = memberCommandService.changePassword(authMemberId, dto);
        return ApiResponse.onSuccess(response);
    }
}
