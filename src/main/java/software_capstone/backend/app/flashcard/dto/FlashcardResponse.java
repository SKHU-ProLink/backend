package software_capstone.backend.app.flashcard.dto;

import java.util.List;

public record FlashcardResponse(String sessionId, List<WordItem> words) {

    public record WordItem(
            String word,
            String partOfSpeech,
            String pronunciation,
            String meaning
    ) {}
}
