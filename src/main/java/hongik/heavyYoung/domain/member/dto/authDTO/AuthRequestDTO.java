package hongik.heavyYoung.domain.member.dto.authDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

public class AuthRequestDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthSignUpRequestDTO {
        @Schema(description = "이메일", example = "heavyyoung@g.hongik.ac.kr")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "올바른 학교 이메일 형식을 입력해주세요.")
        private String email;

        @Schema(description = "비밀번호")
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,}$",
                message = "비밀번호는 8자 이상이며, 영문/숫자/특수문자를 최소 1개씩 포함해야 합니다."
        )
        private String password;

        @Schema(description = "비밀번호 확인")
        @NotBlank(message = "비밀번호 확인은 필수 입력 값입니다.")
        private String passwordConfirm;

        @Schema(description = "학번", example = "C000000")
        @NotBlank(message = "학번은 필수 입력 값입니다.")
        private String studentId;

        @Schema(description = "이름", example = "회비영")
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        private String studentName;

        @Schema(description = "전화번호", example = "010-0000-0000")
        @NotBlank(message = "전화번호는 필수 입력 값입니다.")
        private String phoneNumber;

    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthLoginRequestDTO {
        @Schema(description = "이메일",example = "heavyyoung@g.hongik.ac.kr")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "올바른 학교 이메일 형식을 입력해주세요.")
        private String email;
        @Schema(description = "비밀번호")
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        private String password;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SendCodeRequestDTO {
        @Schema(description = "이메일", example = "heavyyoung@g.hongik.ac.kr")
        @NotBlank(message = "학교 이메일은 필수 입력 값입니다.")
        @Email(message = "올바른 학교 이메일 형식을 입력해주세요.")
        private String email;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerifyCodeRequestDTO {
        @Schema(description = "이메일", example = "heavyyoung@g.hongik.ac.kr")
        @NotBlank(message = "학교 이메일은 필수 입력 값입니다.")
        @Email(message = "올바른 학교 이메일 형식을 입력해주세요.")
        private String email;
        @Schema(description = "전송된 인증코드", example = "1234")
        @NotBlank(message = "인증 코드는 필수 입력 값입니다.")
        private String code;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangePasswordRequestDTO {
        @Schema(description = "기존 비밀번호")
        @NotBlank(message = "기존 비밀번호는 필수 입력 값입니다.")
        private String originPassword;

        @Schema(description = "새 비밀번호")
        @NotBlank(message = "새 비밀번호는 필수 입력 값입니다.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,}$",
                message = "비밀번호는 8자 이상이며, 영문/숫자/특수문자를 최소 1개씩 포함해야 합니다."
        )
        private String newPassword;

        @Schema(description = "새 비밀번호 확인")
        @NotBlank(message = "새 비밀번호 확인은 필수 입력 값입니다.")
        private String newPasswordConfirm;
    }

}
