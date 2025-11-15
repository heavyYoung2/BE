package hongik.heavyYoung.domain.member.dto.authDTO;

import hongik.heavyYoung.domain.member.enums.MemberRole;
import hongik.heavyYoung.domain.member.enums.MemberStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class AuthResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpResponseDTO {
        @Schema(description = "회원 PK")
        private Long memberId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponseDTO {
        @Schema(description = "회원 PK")
        private Long memberId;

        @Schema(description = "역할")
        private MemberRole role;


        @Schema(description = "학번")
        private String studentId;

        @Schema(description = "JWT 액세스 토큰")
        private String accessToken;

        @Schema(description = "리프레시 토큰")
        private String refreshToken;

        @Schema(description = "만료(초)")
        private long expiresIn;

        @Schema(description = "리프레시 토큰 만료(초)")
        private long refreshExpiresIn;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SendCodeResponseDTO {
        @Schema(description = "전송된 인증코드")
        private String code;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerifyCodeResponseDTO {
        @Schema(description = "이메일 주소", example = "heavyyoung@g.hongik.ac.kr")
        private String email;

        @Schema(description = "결과 메시지", example = "이메일 인증이 완료되었습니다.")
        private String message;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TempPasswordResponseDTO {
        @Schema(description = "이메일", example = "heavyyoung@g.hongik.ac.kr")
        private String email;

        @Schema(description = "결과 메시지", example = "임시 비밀번호가 발급되었습니다.")
        private String message;
    }
}
