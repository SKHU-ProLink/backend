package software_capstone.backend.app.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import software_capstone.backend.app.store.document.category.ItemCategory;
import software_capstone.backend.app.store.dto.request.PurchaseRequest;
import software_capstone.backend.app.store.dto.response.PurchaseResponse;
import software_capstone.backend.app.store.dto.response.ShopItemResponse;

import java.util.List;

public interface ShopControllerDocs {

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
            @ApiResponse(responseCode = "403", description = "토큰을 담아 요청하지 않음"),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음")
    })
    ResponseEntity<List<ShopItemResponse>> getItems(String userId, ItemCategory category);

    @Operation(
            summary = "상점 아이템 구매",
            description =
                    """
                    상점 아이템을 구매합니다.
                    
                    캐시가 부족할 경우 에러가 발생합니다.
                    
                    출시되지 않은 아이템은 구매할 수 없습니다.
                    
                    구매 성공 시 보관함에 아이템이 추가되고 구매 내역이 저장됩니다.
                    
                    토큰으로 받아온 유저가 존재하지 않다면 에러가 발생합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "아이템 구매 성공"),
            @ApiResponse(responseCode = "400", description = "캐시 부족"),
            @ApiResponse(responseCode = "403", description = "토큰을 담아 요청하지 않음"),
            @ApiResponse(responseCode = "404", description = "유저 또는 아이템을 찾을 수 없음")
    })
    ResponseEntity<PurchaseResponse> purchaseItem(String userId, PurchaseRequest request);
}
