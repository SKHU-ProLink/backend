package software_capstone.backend.app.learning.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import software_capstone.backend.app.user.document.Difficulty;
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

    @Builder.Default
    private List<Sentence> sentences = new ArrayList<>();

    @Field("quiz_completed")
    private boolean quizCompleted = false;

    @Field("flashcards_completed")
    private boolean flashcardsCompleted = false;

    @Field("sentences_completed")
    private boolean sentencesCompleted = false;

    public void completeFlashcards() {
        this.flashcardsCompleted = true;
    }

    public void updateSentences(List<Sentence> sentences) {
        this.sentences = new ArrayList<>(sentences);
    }

    public void complete() {
        this.status = Status.COMPLETED;
    }

}
