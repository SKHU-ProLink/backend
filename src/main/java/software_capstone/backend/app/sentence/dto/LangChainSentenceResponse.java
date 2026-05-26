package software_capstone.backend.app.sentence.dto;

import java.util.List;

public record LangChainSentenceResponse(List<SentenceItem> sentences) {

    public record SentenceItem(String word, String sentence, String meaning) {}
}
