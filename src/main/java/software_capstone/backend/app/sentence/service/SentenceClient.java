package software_capstone.backend.app.sentence.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import software_capstone.backend.app.sentence.dto.LangChainSentenceRequest;
import software_capstone.backend.app.sentence.dto.LangChainSentenceResponse;
import software_capstone.backend.global.exception.BadRequestException;
import software_capstone.backend.global.exception.ErrorMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class SentenceClient {

    private final WebClient webClient;

    @Value("${langchain.base-url}")
    private String langchainBaseUrl;

    public LangChainSentenceResponse createSentences(LangChainSentenceRequest request) {
        log.info("[SentenceClient] 예문 생성 요청 - level: {}, wordCount: {}", request.level(), request.words().size());

        return webClient.post()
                .uri(langchainBaseUrl + "/sentence")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(status -> status.isError(), res ->
                        res.bodyToMono(String.class)
                                .map(body -> new BadRequestException(ErrorMessage.LANGCHAIN_SERVER_ERROR)))
                .bodyToMono(LangChainSentenceResponse.class)
                .block();
    }
}
