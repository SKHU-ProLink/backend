package software_capstone.backend.app.sentence.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import software_capstone.backend.app.user.document.Difficulty;

import java.util.List;

public record LangChainSentenceRequest(List<WordItem> words, String level) {

    public LangChainSentenceRequest(List<WordItem> words, Difficulty difficulty) {
        this(words, difficulty.name());
    }

    public record WordItem(
            String word,
            @JsonProperty("part_of_speech") String partOfSpeech,
            String pronunciation,
            String meaning
    ) {}
}
