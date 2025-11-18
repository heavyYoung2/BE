package hongik.heavyYoung.domain.item.controller.general;

import hongik.heavyYoung.domain.item.dto.ItemResponseDTO;
import hongik.heavyYoung.domain.item.service.general.ItemQueryService;
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

@Tag(name = "Item API - 학생", description = "학생 - 물품 관련 API")
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemRestController {

    private final ItemQueryService itemQueryService;

    @PreAuthorize("hasRole(\"USER\")")
    @Operation(summary = "대여 가능한 물품 조회")
    @GetMapping("")
    public ApiResponse<ItemResponseDTO.ItemList> getAllItems(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        return ApiResponse.onSuccess(itemQueryService.getAllItems());
    }
}
