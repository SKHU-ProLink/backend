package software_capstone.backend.app.flashcard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software_capstone.backend.app.flashcard.dto.FlashcardResponse;
import software_capstone.backend.app.flashcard.dto.LangChainFlashcardResponse;
import software_capstone.backend.app.flashcard.dto.PronunciationResponse;
import software_capstone.backend.app.learning.document.Flashcard;
import software_capstone.backend.app.learning.document.LearningSession;
import software_capstone.backend.app.learning.repository.LearningSessionRepository;
import software_capstone.backend.app.user.document.User;
import software_capstone.backend.app.user.service.UserService;
import software_capstone.backend.global.exception.BadRequestException;
import software_capstone.backend.global.exception.ErrorMessage;
import software_capstone.backend.global.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlashcardService {

    private final FlashcardClient flashcardClient;
    private final UserService userService;
    private final LearningSessionRepository learningSessionRepository;

    // 플래시카드 단어 목록 조회
    public FlashcardResponse getFlashcard(String userId) {
        User user = userService.findUserById(userId);

        if (user.getDifficulty() == null) {
            throw new BadRequestException(ErrorMessage.USER_NOT_ONBOARDED);
        }

        log.info("[Flashcard] 단어 생성 요청 - userId: {}, difficulty: {}", userId, user.getDifficulty());

        LangChainFlashcardResponse langChainResponse = flashcardClient.generateWords(user.getDifficulty());

        List<Flashcard> flashcards = langChainResponse.words().stream()
                .map(w -> Flashcard.builder()
                        .word(w.word())
                        .partOfSpeech(w.partOfSpeech())
                        .pronunciation(w.pronunciation())
                        .meaning(w.meaning())
                        .build())
                .toList();

        LearningSession saved = learningSessionRepository.save(
                LearningSession.builder()
                        .userId(userId)
                        .date(LocalDate.now())
                        .difficulty(user.getDifficulty())
                        .flashcards(flashcards)
                        .build()
        );

        log.info("[Flashcard] 학습 세션 생성 완료 - userId: {}", userId);

        List<FlashcardResponse.WordItem> wordItems = langChainResponse.words().stream()
                .map(w -> new FlashcardResponse.WordItem(w.word(), w.partOfSpeech(), w.pronunciation(), w.meaning()))
                .toList();

        return new FlashcardResponse(saved.getId(), wordItems);
    }

    // 플래시카드 학습 완료 상태로 변경
    public void completeFlashcard(String userId, String sessionId) {
        LearningSession session = learningSessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.LEARNING_SESSION_NOT_FOUND));

        if (!session.getUserId().equals(userId)) {
            throw new BadRequestException(ErrorMessage.SESSION_USER_MISMATCH);
        }

        if (session.isFlashcardsCompleted()) {
            throw new BadRequestException(ErrorMessage.ALREADY_COMPLETED_SESSION);
        }

        session.completeFlashcards();
        learningSessionRepository.save(session);

        log.info("[Flashcard] 학습 완료 처리 - userId: {}, sessionId: {}", userId, sessionId);
    }

    // 발음 판정
    public PronunciationResponse getPronunciationFeedback(
            String userId, MultipartFile audioFile, String targetWord) {
        if (audioFile == null || audioFile.isEmpty()) {
            throw new BadRequestException(ErrorMessage.EMPTY_AUDIO_FILE);
        }

        log.info("[Flashcard] 발음 판정 요청 - userId: {}, targetWord: {}", userId, targetWord);

        return flashcardClient.getPronunciationFeedback(audioFile, targetWord);
    }
}
