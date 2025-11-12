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

        @Schema(description = "이메일")
        private String email;

        @Schema(description = "역할")
        private MemberRole role;

        @Schema(description = "상태")
        private MemberStatus status;

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
        @Schema(description = "전송된 인증코드 (개발용)")
        private String code;
    }
}
