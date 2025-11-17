package hongik.heavyYoung.domain.member.controller;

import hongik.heavyYoung.domain.member.dto.MemberResponseDTO;
import hongik.heavyYoung.domain.member.service.admin.AdminMemberCommandService;
import hongik.heavyYoung.domain.member.service.admin.AdminMemberQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.security.auth.AuthMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Member API - 학생회", description = "학생회 인원 관리 API")
@RequestMapping("/admin/council")
@RequiredArgsConstructor
public class AdminMemberRestController {

    private final AdminMemberCommandService adminMemberCommandService;
    private final AdminMemberQueryService adminMemberQueryService;

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "학생회 인원 전체 조회")
    @GetMapping
    public ApiResponse<MemberResponseDTO.StudentCouncilInfo> getStudentCouncil(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        MemberResponseDTO.StudentCouncilInfo studentCouncilMembers = adminMemberQueryService.findStudentCouncil();
        return ApiResponse.onSuccess(studentCouncilMembers);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "학생회 인원 추가")
    @PatchMapping("/{memberId}/add")
    public ApiResponse<Void> addStudentCouncil(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @PathVariable("memberId") Long memberId
    ) {
        adminMemberCommandService.createStudentCouncil(memberId);
        return ApiResponse.onSuccess(null);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "학생회에 추가할 학생 조회")
    @GetMapping("/lookup")
    public ApiResponse<MemberResponseDTO.StudentCouncilCandidateInfo> getStudentCouncilCandidate(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @Parameter(description = "학번", example = "C011117")
            @RequestParam(value = "studentId", required = false) String studentId
    ) {
        MemberResponseDTO.StudentCouncilCandidateInfo studentCouncilCandidate = adminMemberQueryService.findStudentCouncilCandidate(studentId);
        return ApiResponse.onSuccess(studentCouncilCandidate);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "학생회 삭제")
    @PatchMapping("/{memberId}/delete")
    public ApiResponse<Void> deleteStudentCouncil(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @PathVariable("memberId") Long memberId
    ) {
        adminMemberCommandService.deleteStudentCouncil(memberId);
        return ApiResponse.onSuccess(null);
    }
}
