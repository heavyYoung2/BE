package hongik.heavyYoung.domain.studentFee.controller.general;

import hongik.heavyYoung.domain.studentFee.service.general.StudentFeeCommandService;
import hongik.heavyYoung.global.qr.QrTokenResponse;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.security.auth.AuthMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "StudentFee API", description = "학생회비 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("members/fee/qr-tokens")
public class StudentFeeRestController {

    private final StudentFeeCommandService studentFeeCommandService;

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "학생회비 납부여부 QR 생성")
    @GetMapping("")
    public ApiResponse<QrTokenResponse> generateStudentFeeQrToken(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        return ApiResponse.onSuccess(studentFeeCommandService.generateStudentFeeQrToken(authMemberId));
    }
}
