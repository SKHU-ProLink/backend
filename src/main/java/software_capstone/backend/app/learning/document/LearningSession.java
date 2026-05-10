package software_capstone.backend.app.learning.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import software_capstone.backend.app.abocado.document.Difficulty;
import software_capstone.backend.global.document.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "learning_sessions")
public class LearningSession extends BaseEntity {

    @Indexed
    private String userId;

    private LocalDate date;

    private Difficulty difficulty;

    @Builder.Default
    private Status status = Status.IN_PROGRESS;

    @Builder.Default
    private List<Flashcard> flashcards = new ArrayList<>();

    @Field("quiz_completed")
    @Builder.Default
    private boolean quizCompleted = false;

    @Field("flashcards_completed")
    @Builder.Default
    private boolean flashcardsCompleted = false;

    public void completeFlashcards() {
        this.flashcardsCompleted = true;
    }

    public void complete() {
        this.status = Status.COMPLETED;
    }

}
