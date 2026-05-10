package software_capstone.backend.app.flashcard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record LangChainFlashcardResponse(List<WordItem> words) {

    public record WordItem(
            String word,

            @JsonProperty("part_of_speech")
            String partOfSpeech,

            String pronunciation,
            String meaning
    ) {}
}
