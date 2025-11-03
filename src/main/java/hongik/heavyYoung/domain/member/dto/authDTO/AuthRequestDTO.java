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
    }
}
