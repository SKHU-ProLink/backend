package software_capstone.backend.app.flashcard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import software_capstone.backend.app.abocado.document.Difficulty;
import software_capstone.backend.app.flashcard.dto.LangChainFlashcardResponse;
import software_capstone.backend.app.flashcard.dto.PronunciationResponse;
import software_capstone.backend.global.exception.BadRequestException;
import software_capstone.backend.global.exception.ErrorMessage;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FlashcardClient {

    private final WebClient webClient;

    @Value("${langchain.base-url}")
    private String langchainBaseUrl;

    public LangChainFlashcardResponse generateWords(Difficulty difficulty) {
        log.info("[FlashcardClient] 단어 생성 요청 - difficulty: {}", difficulty);

        return webClient.post()
                .uri(langchainBaseUrl + "/flashcard")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("level", difficulty.name()))
                .retrieve()
                .onStatus(status -> status.isError(), res ->
                        res.bodyToMono(String.class)
                                .map(body -> new BadRequestException(ErrorMessage.LANGCHAIN_SERVER_ERROR)))
                .bodyToMono(LangChainFlashcardResponse.class)
                .block();
    }

    public PronunciationResponse getPronunciationFeedback(MultipartFile audioFile, String targetWord) {
        log.info("[FlashcardClient] 발음 판정 요청 - targetWord: {}", targetWord);

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("audio_file", audioFile.getResource());
        builder.part("target_word", targetWord);

        return webClient.post()
                .uri(langchainBaseUrl + "/flashcard/pronunciation")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .onStatus(status -> status.isError(), res ->
                        res.bodyToMono(String.class)
                                .map(body -> new BadRequestException(ErrorMessage.LANGCHAIN_SERVER_ERROR)))
                .bodyToMono(PronunciationResponse.class)
                .block();
    }
}
