package software_capstone.backend.app.flashcard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PronunciationResponse(
        @JsonProperty("is_correct")
        boolean isCorrect,

        @JsonProperty("recognized_text")
        String recognizedText,

        String feedback
) {}
