package hongik.heavyYoung.domain.item.controller.admin;

import hongik.heavyYoung.domain.item.dto.ItemRequestDTO;
import hongik.heavyYoung.domain.item.dto.ItemResponseDTO;
import hongik.heavyYoung.domain.item.service.admin.AdminItemCommandService;
import hongik.heavyYoung.domain.item.service.general.ItemQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.security.auth.AuthMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Item API - 학생회", description = "학생회 - 물품 관련 API")
@RestController
@RequestMapping("/admin/items")
@RequiredArgsConstructor
public class AdminItemRestController {

    private final ItemQueryService itemQueryService;
    private final AdminItemCommandService adminItemCommandService;

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "대여 가능한 물품 조회")
    @GetMapping("")
    public ApiResponse<ItemResponseDTO.ItemList> getAllItems(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        return ApiResponse.onSuccess(itemQueryService.getAllItems());
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "개별 물품 수량 증가")
    @PatchMapping("")
    public ApiResponse<?> increaseItemQuantity(
            @RequestBody ItemRequestDTO.Increase request,
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        adminItemCommandService.increaseItemQuantity(request);
        return ApiResponse.onSuccess(null);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "물품 리스트 조회")
    @GetMapping("/{item-category-id}")
    public ApiResponse<ItemResponseDTO.ItemListByCategory> getItemListByCategoryId(
            @PathVariable("item-category-id") Long itemCategoryId,
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        return ApiResponse.onSuccess(itemQueryService.getItemListByCategoryId(itemCategoryId));
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "개별 물품 삭제")
    @DeleteMapping("/{item-id}")
    public ApiResponse<?> deleteItem(
            @PathVariable("item-id") Long itemId,
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        adminItemCommandService.deleteItem(itemId);
        return ApiResponse.onSuccess(null);
    }

}
