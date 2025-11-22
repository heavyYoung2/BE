package hongik.heavyYoung.domain.member.controller.general;

import hongik.heavyYoung.domain.member.dto.authDTO.AuthRequestDTO;
import hongik.heavyYoung.domain.member.dto.authDTO.AuthResponseDTO;
import hongik.heavyYoung.domain.member.service.general.impl.AuthService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.security.auth.AuthMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Validated
@Tag(name = "Auth API - 로그인 및 회원가입", description = "회원 - 로그인 및 회원가입 관련 API")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @Operation(summary = "학교 이메일 인증번호 요청 API",
            description = "학교 이메일을 통해 인증 번호를 전송합니다.")
    @PostMapping("/send-code")
    public ApiResponse<AuthResponseDTO.SendCodeResponseDTO> sendCode(
            @RequestBody @Valid AuthRequestDTO.SendCodeRequestDTO dto
    ) {
        return ApiResponse.onSuccess(authService.issueSchoolEmailCode(dto));
    }


    @Operation(summary = "학교 이메일 인증번호 인증 API",
                description = "학교 이메일을 통해 받은 인증번호를 인증합니다.")
    @PostMapping("/verify-code")
    public ApiResponse<AuthResponseDTO.VerifyCodeResponseDTO> verifyCode(
            @RequestBody @Valid AuthRequestDTO.VerifyCodeRequestDTO dto
    ) {
        return ApiResponse.onSuccess(authService.verifySchoolEmailCode(dto));
    }
    // TODO : redis 설정 이후 javaMailSender로 구현 이후 구현


    @Operation(summary = "회원 가입 API",
                description = "이메일, 비밀번호, 이름, 학번, 학과, 전화번호를 통해 회원가입을 합니다.")
    @PostMapping ("/sign-in")
    public ApiResponse<AuthResponseDTO.SignUpResponseDTO> signIn(
            @RequestBody @Valid AuthRequestDTO.AuthSignUpRequestDTO dto
    ) {
        AuthResponseDTO.SignUpResponseDTO authResponseDTO = authService.signUp(dto);
        return ApiResponse.onSuccess(authResponseDTO);
    }


    @Operation(summary = "로그인 API",
                description = "이메일, 비밀번호를 통해 로그인을 합니다.")
    @PostMapping ("/login")
    public ApiResponse<AuthResponseDTO.LoginResponseDTO> login(
            @RequestBody @Valid AuthRequestDTO.AuthLoginRequestDTO dto
    ) {
        return ApiResponse.onSuccess(authService.login(dto));
    }

    @PreAuthorize("hasRole(\"USER\")")
    @Operation(summary = "로그아웃 API",
                description = "로그아웃을 합니다.")
    @PostMapping ("/logout")
    public ApiResponse<String> logout(
            @Parameter(hidden = true) @AuthMemberId Long memberId
    ) {
        authService.logout(memberId);
        return ApiResponse.onSuccess("로그아웃이 완료되었습니다.");
    }

    @Operation(summary = "임시 비밀번호 발급 API",
                description = "등록된 이메일을 통해 임시 비밀번호가 발급됩니다. 발급이 되는 즉시 비밀번호가 업데이트 됩니다.")
    @PostMapping("/tmp-password")
    public  ApiResponse<AuthResponseDTO.TempPasswordResponseDTO> issueTemporaryPassword(
            @RequestParam("email") String email
    ) {
        AuthResponseDTO.TempPasswordResponseDTO response = authService.issueTemporaryPassword(email);
        return ApiResponse.onSuccess(response);
    }


    @Operation(summary = "비밀번호 변경",
    description = "기존 비밀번호, 새 비밀번호, 새 비밀번호 확인을 통해 비밀번호를 변경합니다.")
    @PostMapping("/change-password")
    public ApiResponse<AuthResponseDTO.ChangePasswordResponseDTO> sendEmail(@Parameter(hidden = true) @AuthMemberId Long authMemberId,
                                                                      @RequestBody @Valid AuthRequestDTO.ChangePasswordRequestDTO dto) {
        AuthResponseDTO.ChangePasswordResponseDTO response = authService.changePassword(authMemberId, dto);
        return ApiResponse.onSuccess(response);
    }


}
