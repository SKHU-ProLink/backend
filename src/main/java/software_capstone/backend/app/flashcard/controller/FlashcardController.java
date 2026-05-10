package software_capstone.backend.app.flashcard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software_capstone.backend.app.auth.jwt.TokenProvider;
import software_capstone.backend.app.flashcard.dto.FlashcardResponse;
import software_capstone.backend.app.flashcard.dto.PronunciationResponse;
import software_capstone.backend.app.flashcard.service.FlashcardService;

@Tag(name = "Flashcard", description = "플래시카드 학습 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/flashcard")
public class FlashcardController {

    private final FlashcardService flashcardService;

    @Operation(
            summary = "오늘의 플래시카드 조회",
            description = """
                    오늘의 플래시카드 단어 목록을 조회합니다.

                    유저의 난이도에 맞는 단어 5개를 AI 서버에서 생성하여 반환합니다.

                    학습 세션이 생성되며, 반환된 sessionId는 이후 학습 완료 요청에 사용됩니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "플래시카드 조회 성공"),
            @ApiResponse(responseCode = "400", description = "온보딩이 완료되지 않은 유저"),
            @ApiResponse(responseCode = "403", description = "토큰을 담아 요청하지 않음"),
            @ApiResponse(responseCode = "404", description = "유저가 존재하지 않음")
    })
    @GetMapping
    public ResponseEntity<FlashcardResponse> getFlashcard(
            @AuthenticationPrincipal TokenProvider.AuthUser authUser
    ) {
        return ResponseEntity.ok(flashcardService.getFlashcard(authUser.userId()));
    }

    @Operation(
            summary = "플래시카드 학습 완료",
            description = """
                    플래시카드 학습을 완료 처리합니다.

                    조회 시 반환된 sessionId를 경로에 담아 요청해야 합니다.

                    퀴즈까지 완료된 경우 학습 세션 전체가 COMPLETED 상태로 변경됩니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "학습 완료 처리 성공"),
            @ApiResponse(responseCode = "400", description = "이미 완료된 세션이거나 세션 소유자가 아님"),
            @ApiResponse(responseCode = "403", description = "토큰을 담아 요청하지 않음"),
            @ApiResponse(responseCode = "404", description = "세션이 존재하지 않음")
    })
    @PostMapping("/complete/{session-id}")
    public ResponseEntity<Void> completeFlashcard(
            @AuthenticationPrincipal TokenProvider.AuthUser authUser,
            @PathVariable("session-id") String sessionId
    ) {
        flashcardService.completeFlashcard(authUser.userId(), sessionId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "발음 판정",
            description = """
                    녹음된 오디오 파일을 AI 서버에 전송하여 발음을 판정합니다.

                    multipart/form-data 형식으로 오디오 파일(audio_file)과 목표 단어(target_word)를 함께 전송해야 합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "발음 판정 성공"),
            @ApiResponse(responseCode = "400", description = "오디오 파일이 비어있음"),
            @ApiResponse(responseCode = "403", description = "토큰을 담아 요청하지 않음")
    })
    @PostMapping(value = "/pronunciation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PronunciationResponse> getPronunciationFeedback(
            @AuthenticationPrincipal String userId,
            @RequestPart MultipartFile audioFile,
            @RequestParam String targetWord
    ) {
        return ResponseEntity.ok(flashcardService.getPronunciationFeedback(userId, audioFile, targetWord));
    }
}
