package hongik.heavyYoung.domain.studentFee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentFeeRequestDTO {
    @Schema(description = "qr 토큰")
    @NotBlank(message = "qr 토큰은 필수입니다.")
    private String qrToken;
}
