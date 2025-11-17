package hongik.heavyYoung.domain.item.controller;

import hongik.heavyYoung.domain.item.dto.ItemCategoryRequestDTO;
import hongik.heavyYoung.domain.item.dto.ItemCategoryResponseDTO;
import hongik.heavyYoung.domain.item.service.ItemCategoryCommandService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.security.auth.AuthMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ItemCategory API - 학생회", description = "학생회 - 물품 종류 관련 API")
@RestController
@RequestMapping("/admin/item-categories")
@RequiredArgsConstructor
public class AdminItemCategoryRestController {

    private final ItemCategoryCommandService itemCategoryService;

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "물품 종류 추가")
    @PostMapping("")
    public ApiResponse<ItemCategoryResponseDTO.Create> createItemCategory(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @RequestBody ItemCategoryRequestDTO.Create itemCategoryName
    ) {
        return ApiResponse.onSuccess(itemCategoryService.createItemCategory(itemCategoryName));
    }

}
