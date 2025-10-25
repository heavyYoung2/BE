package hongik.heavyYoung.domain.studentFee.controller;

import hongik.heavyYoung.domain.studentFee.service.StudentFeeQueryService;
import hongik.heavyYoung.global.qr.QrTokenResponse;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "StudentFee API", description = "학생회비 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("members/fee/qr-tokens")
public class StudentFeeRestController {

    private final StudentFeeQueryService studentFeeQueryService;

    @Operation(summary = "학생회비 납부여부 QR 생성")
    @GetMapping("")
    public ApiResponse<QrTokenResponse> generateStudentFeeQrToken() {
        return ApiResponse.onSuccess(studentFeeQueryService.generateStudentFeeQrToken());
    }
}
