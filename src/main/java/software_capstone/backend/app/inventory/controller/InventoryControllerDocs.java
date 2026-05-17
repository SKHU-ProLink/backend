package software_capstone.backend.app.inventory.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import software_capstone.backend.app.inventory.dto.InventoryResponse;

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
    ResponseEntity<List<InventoryResponse>> getInventory(String userId);
}
