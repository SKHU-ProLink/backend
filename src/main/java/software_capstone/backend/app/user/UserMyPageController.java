package software_capstone.backend.app.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software_capstone.backend.app.user.dto.UserMyPageResponse;
import software_capstone.backend.app.user.service.UserMyPageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class UserMyPageController {

    private final UserMyPageService userMyPageService;

    @Operation(
            summary = "유저 마이페이지 조회",
            description =
                    """
                    유저의 마이페이지 조회 시에 사용합니다.
                    
                    유저의 이름, 현재 키우고 있는 아보카도, 완전히 키운 아보카도 목록을 반환합니다.
                    
                    추후 학습 관련 기능이 추가되면, 학습 날짜도 같이 반환합니다.
                    
                    토큰으로 받아온 유저가 없다면 에러가 발생합니다.
                    
                    유저에게 현재 활성화된 아보카도가 없다면 에러가 발생합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "마이페이지 조회 성공"),
            @ApiResponse(responseCode = "403", description = "토큰을 담아 요청하지 않음"),
            @ApiResponse(responseCode = "404", description = "유저가 존재하지 않거나 활성화된 아보카도가 존재하지 않음")
    })
    @GetMapping
    public ResponseEntity<UserMyPageResponse> getMyPage(
            @AuthenticationPrincipal String userId
    ) {
        return ResponseEntity.ok(userMyPageService.getUserMyPage(userId));
    }
}
