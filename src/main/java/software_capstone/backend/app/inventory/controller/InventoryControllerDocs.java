package software_capstone.backend.app.inventory.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import software_capstone.backend.app.auth.jwt.TokenProvider;
import software_capstone.backend.app.inventory.dto.request.FeedRequest;
import software_capstone.backend.app.inventory.dto.response.FeedResponse;
import software_capstone.backend.app.inventory.dto.response.InventoryResponse;

import java.util.List;

public interface InventoryControllerDocs {
    @Operation(
            summary = "보관함 조회",
            description =
                    """
                    유저의 보관함 아이템 목록을 조회합니다.
                    
                    토큰으로 받아온 유저가 존재하지 않다면 에러가 발생합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "보관함 조회 성공"),
            @ApiResponse(responseCode = "403", description = "토큰을 담아 요청하지 않음"),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음")
    })
    ResponseEntity<List<InventoryResponse>> getInventory(TokenProvider.AuthUser authUser);

    @Operation(
            summary = "아이템 급여",
            description =
                    """
                    보관함 아이템을 아보카도에 급여합니다.
                    
                    급여 시 아이템 등급에 따라 XP가 지급됩니다.
                    (C급 +5, B급 +10, A급 +20)
                    
                    보유 수량이 0이면 에러가 발생합니다.
                    
                    만일 MAX 레벨에 도달한 경우, 캐릭터의 경험치 값을 -1로 반환합니다.
                    
                    토큰으로 받아온 유저가 존재하지 않다면 에러가 발생합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "아이템 급여 성공"),
            @ApiResponse(responseCode = "400", description = "아이템 수량 부족"),
            @ApiResponse(responseCode = "403", description = "토큰을 담아 요청하지 않음"),
            @ApiResponse(responseCode = "404", description = "아이템 또는 아보카도를 찾을 수 없음")
    })
    ResponseEntity<FeedResponse> feedItem(TokenProvider.AuthUser authUser, FeedRequest request);
}
