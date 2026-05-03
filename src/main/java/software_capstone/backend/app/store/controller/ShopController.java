package software_capstone.backend.app.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software_capstone.backend.app.store.document.category.ItemCategory;
import software_capstone.backend.app.store.dto.ShopItemResponse;
import software_capstone.backend.app.store.service.ShopService;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @Operation(
            summary = "상점 아이템 목록 조회",
            description =
                    """
                    상점에 등록된 아이템 목록을 조회합니다.
                    
                    category 파라미터를 통해 카테고리별 필터링이 가능합니다.
                    
                    category를 담지 않으면 전체 아이템을 반환합니다.
                    
                    category: FERTILIZER(비료), NUTRIENT(영양제), DECORATION(꾸미기)
                    
                    토큰으로 받아온 유저가 존재하지 않다면 에러가 발생합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상점 아이템 목록 조회 성공"),
            @ApiResponse(responseCode = "403", description = "토큰을 담아 요청하지 않음")
    })
    @GetMapping("/items")
    public ResponseEntity<List<ShopItemResponse>> getItems(
            @AuthenticationPrincipal String userId,
            @RequestParam(required = false) ItemCategory category
    ) {
        return ResponseEntity.ok(shopService.getItems(category));
    }
}
