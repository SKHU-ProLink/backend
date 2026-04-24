package software_capstone.backend.app.abocado.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software_capstone.backend.app.abocado.dto.AbocadoOnboardingRequest;
import software_capstone.backend.app.abocado.service.AbocadoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/abocado")
public class AbocadoController {

    private final AbocadoService abocadoService;

    @Operation(
            summary = "유저 온보딩",
            description =
                    """
                    새로운 유저가 서비스에 처음 접근 시에 사용합니다.
                    
                    새로운 아보카도에 대한 이름, 유저가 설정한 난이도를 body에 담아 요청해야 합니다.
                    
                    생성 이후, 반환값은 없지만 자동적으로 아보카도가 생성되며 레벨과 경험치를 초기값으로 설정합니다.
                    
                    토큰으로 받아온 유저가 존재하지 않다면 에러가 발생합니다.
                    
                    difficulty: EASY, NORMAL, HARD
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "온보딩 요청 성공"),
            @ApiResponse(responseCode = "403", description = "토큰을 담아 요청하지 않음"),
            @ApiResponse(responseCode = "404", description = "유저가 존재하지 않음")
    })
    @PostMapping("/onboarding")
    public ResponseEntity<Void> onBoarding(
            @AuthenticationPrincipal String userId,
            AbocadoOnboardingRequest request
    ) {
        abocadoService.onBoarding(userId, request);
        return ResponseEntity.ok().build();
    }
}
