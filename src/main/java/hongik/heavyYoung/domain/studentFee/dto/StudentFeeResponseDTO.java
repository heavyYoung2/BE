package hongik.heavyYoung.domain.studentFee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentFeeResponseDTO {
    @Schema(description = "학생회비 납부 결과")
    private boolean approved;
}
