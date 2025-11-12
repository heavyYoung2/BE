package hongik.heavyYoung.domain.member.dto.authDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

public class AuthRequestDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthSignUpRequestDTO {
        @Schema(description = "이메일")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        private String email;

        @Schema(description = "비밀번호")
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        private String password;

        @Schema(description = "비밀번호 확인")
        @NotBlank(message = "비밀번호 확인은 필수 입력 값입니다.")
        private String passwordConfirm;

        @Schema(description = "학번")
        @NotBlank(message = "학번은 필수 입력 값입니다.")
        private String studentId;

        @Schema(description = "이름")
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        private String studentName;

        @Schema(description = "전화번호")
        @NotBlank(message = "전화번호는 필수 입력 값입니다.")
        private String phoneNumber;

    }

}
