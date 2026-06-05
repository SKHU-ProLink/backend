package software_capstone.backend.app.wallet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import software_capstone.backend.app.auth.jwt.TokenProvider;
import software_capstone.backend.app.wallet.dto.WalletResponse;

public interface WalletControllerDocs {
    @Operation(
            summary = "보유 캐시 / 골드 조회",
            description =
                    """
                    유저의 보유 캐시와 골드를 조회합니다.
                    
                    토큰으로 받아온 유저가 존재하지 않다면 에러가 발생합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "캐시 / 골드 조회 성공"),
            @ApiResponse(responseCode = "403", description = "토큰을 담아 요청하지 않음"),
            @ApiResponse(responseCode = "404", description = "유저 또는 지갑을 찾을 수 없음")
    })
    ResponseEntity<WalletResponse> getWallet(TokenProvider.AuthUser authUser);
}
