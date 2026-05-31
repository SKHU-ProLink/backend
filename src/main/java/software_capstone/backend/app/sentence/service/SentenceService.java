package software_capstone.backend.app.sentence.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software_capstone.backend.app.learning.document.LearningSession;
import software_capstone.backend.app.learning.document.Sentence;
import software_capstone.backend.app.learning.repository.LearningSessionRepository;
import software_capstone.backend.app.sentence.dto.LangChainSentenceRequest;
import software_capstone.backend.app.sentence.dto.LangChainSentenceResponse;
import software_capstone.backend.app.sentence.dto.SentenceCompleteResponse;
import software_capstone.backend.app.sentence.dto.SentenceResponse;
import software_capstone.backend.global.exception.BadRequestException;
import software_capstone.backend.global.exception.ErrorMessage;
import software_capstone.backend.global.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SentenceService {

    private final SentenceClient sentenceClient;
    private final LearningSessionRepository learningSessionRepository;

    // 예문 생성
    public SentenceResponse createSentences(String userId, String sessionId) {
        LearningSession session = learningSessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.LEARNING_SESSION_NOT_FOUND));

        if (!session.getUserId().equals(userId)) {
            throw new BadRequestException(ErrorMessage.SESSION_USER_MISMATCH);
        }

        if (!session.isFlashcardsCompleted()) {
            throw new BadRequestException(ErrorMessage.FLASHCARDS_NOT_COMPLETED);
        }

        log.info("[Sentence] 예문 생성 요청 - userId: {}, sessionId: {}", userId, sessionId);

        List<LangChainSentenceRequest.WordItem> wordItems = session.getFlashcards().stream()
                .map(f -> new LangChainSentenceRequest.WordItem(
                        f.getWord(),
                        f.getPartOfSpeech(),
                        f.getPronunciation(),
                        f.getMeaning()
                ))
                .toList();

        LangChainSentenceRequest request = new LangChainSentenceRequest(wordItems, session.getDifficulty());
        LangChainSentenceResponse langChainResponse = sentenceClient.createSentences(request);

        List<Sentence> sentences = langChainResponse.sentences().stream()
                .map(s -> Sentence.builder()
                        .word(s.word())
                        .sentence(s.sentence())
                        .meaning(s.meaning())
                        .build())
                .toList();

        session.updateSentences(sentences);
        learningSessionRepository.save(session);

        log.info("[Sentence] 예문 생성 완료 - userId: {}, sessionId: {}", userId, sessionId);

        List<SentenceResponse.SentenceItem> responseItems = langChainResponse.sentences().stream()
                .map(s -> new SentenceResponse.SentenceItem(s.word(), s.sentence(), s.meaning()))
                .toList();

        return new SentenceResponse(sessionId, responseItems);
    }

    // 예문 학습 완료 상태로 변경
    public SentenceCompleteResponse completeSentences(String userId, String sessionId) {
        LearningSession session = learningSessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.LEARNING_SESSION_NOT_FOUND));

        if (!session.getUserId().equals(userId)) {
            throw new BadRequestException(ErrorMessage.SESSION_USER_MISMATCH);
        }

        if (session.isSentencesCompleted()) {
            throw new BadRequestException(ErrorMessage.ALREADY_COMPLETED_SENTENCES);
        }

        session.completeSentences();
        learningSessionRepository.save(session);

        log.info("[Sentence] 예문 학습 완료 처리 - userId: {}, sessionId: {}", userId, sessionId);

        int totalSentencesToday = learningSessionRepository
                .findAllByUserIdAndDate(userId, LocalDate.now())
                .stream()
                .mapToInt(s -> s.getSentences().size())
                .sum();

        return new SentenceCompleteResponse(totalSentencesToday);
    }
}
