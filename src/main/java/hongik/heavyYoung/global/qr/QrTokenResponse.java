package hongik.heavyYoung.global.qr;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrTokenResponse {
    @Schema(description = "QR내부 정보")
    private String qrToken;
    @Schema(description = "학생회비 납부 여부")
    private boolean studentFeePaid;
}
