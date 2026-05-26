package software_capstone.backend.app.sentence.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software_capstone.backend.app.auth.jwt.TokenProvider;
import software_capstone.backend.app.sentence.dto.SentenceResponse;
import software_capstone.backend.app.sentence.service.SentenceService;

@Tag(name = "Sentence", description = "예문 학습 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sentence")
public class SentenceController {

    private final SentenceService sentenceService;

    @Operation(
            summary = "예문 생성",
            description = """
                    플래시카드 학습이 완료된 세션의 단어를 기반으로 영어 예문을 생성합니다.

                    플래시카드 학습 완료 후 반환된 sessionId를 요청 바디에 담아 요청해야 합니다.

                    AI 서버에서 각 단어에 맞는 예문을 생성하여 반환합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "예문 생성 성공"),
            @ApiResponse(responseCode = "400", description = "플래시카드 학습 미완료이거나 세션 소유자가 아님"),
            @ApiResponse(responseCode = "403", description = "토큰을 담아 요청하지 않음"),
            @ApiResponse(responseCode = "404", description = "세션이 존재하지 않음")
    })
    @PostMapping
    public ResponseEntity<SentenceResponse> createSentences(
            @AuthenticationPrincipal TokenProvider.AuthUser authUser,
            @RequestParam String sessionId
    ) {
        return ResponseEntity.ok(sentenceService.createSentences(authUser.userId(), sessionId));
    }
}
