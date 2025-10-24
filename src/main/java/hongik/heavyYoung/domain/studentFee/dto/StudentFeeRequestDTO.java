package hongik.heavyYoung.domain.studentFee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentFeeRequestDTO {
    @Schema(description = "qr 토큰")
    private String qrToken;
}
