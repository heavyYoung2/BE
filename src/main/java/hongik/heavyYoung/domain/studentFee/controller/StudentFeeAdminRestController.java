package hongik.heavyYoung.domain.studentFee.controller;

import hongik.heavyYoung.domain.studentFee.dto.StudentFeeRequestDTO;
import hongik.heavyYoung.domain.studentFee.dto.StudentFeeResponseDTO;
import hongik.heavyYoung.domain.studentFee.service.StudentFeeAdminQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.security.auth.AuthMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "StudentFee Admin API", description = "학생회비 관련 API - 학생회")
@RequiredArgsConstructor
@RestController
@RequestMapping("admin/fee/qr-tokens")
public class StudentFeeAdminRestController {

    private final StudentFeeAdminQueryService studentFeeAdminQueryService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "학생회비 납부여부 확인")
    @PostMapping
    public ApiResponse<StudentFeeResponseDTO> verifyStudentFeePaymentByQrToken(
           @Valid @RequestBody StudentFeeRequestDTO studentFeeRequestDTO,
           @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        return ApiResponse.onSuccess(studentFeeAdminQueryService.verifyStudentFeePaymentByQrToken(studentFeeRequestDTO));
    }



}
