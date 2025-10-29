package hongik.heavyYoung.domain.item.controller;

import hongik.heavyYoung.domain.item.dto.ItemResponseDTO;
import hongik.heavyYoung.domain.item.service.ItemQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemRestController {

    private final ItemQueryService itemQueryService;

    @Operation(description = "대여 가능한 물품 조회")
    @GetMapping("")
    public ApiResponse<ItemResponseDTO.ItemList> getAllItems() {
        return ApiResponse.onSuccess(itemQueryService.getAllItems());
    }
}
