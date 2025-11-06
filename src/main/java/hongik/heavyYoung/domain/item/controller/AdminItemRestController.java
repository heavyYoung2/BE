package hongik.heavyYoung.domain.item.controller;

import hongik.heavyYoung.domain.item.dto.ItemRequestDTO;
import hongik.heavyYoung.domain.item.dto.ItemResponseDTO;
import hongik.heavyYoung.domain.item.service.ItemCommandService;
import hongik.heavyYoung.domain.item.service.ItemQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Item API - 학생회", description = "학생회 - 물품 관련 API")
@RestController
@RequestMapping("/admin/items")
@RequiredArgsConstructor
public class AdminItemRestController {

    private final ItemQueryService itemQueryService;
    private final ItemCommandService itemCommandService;

    @Operation(summary = "대여 가능한 물품 조회")
    @GetMapping("")
    public ApiResponse<ItemResponseDTO.ItemList> getAllItems() {
        return ApiResponse.onSuccess(itemQueryService.getAllItems());
    }

    @Operation(summary = "개별 물품 수량 증가")
    @PatchMapping("")
    public ApiResponse<?> increaseItemQuantity(@RequestBody ItemRequestDTO.Increase request) {
        itemCommandService.increaseItemQuantity(request);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "물품 리스트 조회")
    @GetMapping("/{item-category-id}")
    public ApiResponse<ItemResponseDTO.ItemListByCategory> getItemListByCategoryId(@PathVariable("item-category-id") Long itemCategoryId) {
        return ApiResponse.onSuccess(itemQueryService.getItemListByCategoryId(itemCategoryId));
    }

}
