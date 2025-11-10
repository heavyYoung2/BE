package hongik.heavyYoung.domain.member.dto.authDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class AuthResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthSignUpResponseDTO {
        @Schema(description = "회원 PK")
        private Long memberId;
    }
}
