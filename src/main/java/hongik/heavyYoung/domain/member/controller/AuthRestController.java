package hongik.heavyYoung.domain.member.controller;

import hongik.heavyYoung.domain.member.dto.authDTO.AuthRequestDTO;
import hongik.heavyYoung.domain.member.dto.authDTO.AuthResponseDTO;
import hongik.heavyYoung.domain.member.service.AuthService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@Validated
@Tag(name = "Auth API - 로그인 및 회원가입", description = "회원 - 로그인 및 회원가입 관련 API")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @Operation(summary = "학교 이메일 인증번호 요청 API",
            description = "학교 이메일을 통해 인증 번호를 전송합니다.")
    @PostMapping("/send-code")
    public ApiResponse sendCode(){
        return null;
    }

    @Operation(summary = "학교 이메일 인증번호 인증 API")
    @PostMapping("/verify-code")
    public ApiResponse verifyCode(@RequestParam("code") String code){
        return null;
    }
    // TODO : redis 설정 이후 javaMailSender로 구현

    @Operation(summary = "회원 가입 API")
    @PostMapping ("/sign-in")
    public ApiResponse<AuthResponseDTO.AuthSignUpResponseDTO> signIn(
            @RequestBody @Valid AuthRequestDTO.AuthSignUpRequestDTO authRequestDTO
    ){
        AuthResponseDTO.AuthSignUpResponseDTO authResponseDTO = authService.signUp(authRequestDTO);
        return ApiResponse.onSuccess(authResponseDTO);
    }

    @Operation(summary = "회원 정보 입력 API")
    @PostMapping ("/sign-in/add-info")
    public ApiResponse addInfo(){
        return null;
    }

    @Operation(summary = "로그인 API")
    @PostMapping ("/login")
    public ApiResponse login(@RequestParam("email") String email, @RequestParam("password") String password){
        return null;
    }

    @Operation(summary = "로그아웃 API")
    @PostMapping ("/logout")
    public ApiResponse logout(){
        return null;
    }

    @Operation(summary = "임시 비밀번호 발급 API")
    @PostMapping("/tmp-password")
    public  ApiResponse getTmpPassword(@RequestParam("password") String password){
        return null;
    }

}
