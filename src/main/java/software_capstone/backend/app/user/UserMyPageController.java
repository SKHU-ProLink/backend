package software_capstone.backend.app.user;

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

    @GetMapping
    public ResponseEntity<UserMyPageResponse> getMyPage(
            @AuthenticationPrincipal String userId
    ) {
        return ResponseEntity.ok(userMyPageService.getUserMyPage(userId));
    }
}
