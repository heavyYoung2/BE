package hongik.heavyYoung.domain.item.controller;

import hongik.heavyYoung.domain.item.dto.ItemCategoryRequestDTO;
import hongik.heavyYoung.domain.item.dto.ItemCategoryResponseDTO;
import hongik.heavyYoung.domain.item.service.ItemCategoryCommandService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ItemCategory API - 학생회", description = "학생회 - 물품 종류 관련 API")
@RestController
@RequestMapping("/admin/item-categories")
@RequiredArgsConstructor
public class AdminItemCategoryRestController {

    private final ItemCategoryCommandService itemCategoryService;

    @Operation(summary = "물품 종류 추가")
    @PostMapping("")
    public ApiResponse<ItemCategoryResponseDTO.Create> createItemCategory(
            @RequestBody ItemCategoryRequestDTO.Create itemCategoryName
    ) {
        return ApiResponse.onSuccess(itemCategoryService.createItemCategory(itemCategoryName));
    }

}
