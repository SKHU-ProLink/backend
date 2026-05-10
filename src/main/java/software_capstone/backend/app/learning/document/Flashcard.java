package software_capstone.backend.app.learning.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Flashcard {

    private String word;            // 단어

    @Field("part_of_speech")
    private String partOfSpeech;    // 품사

    private String pronunciation;   // 발음 기호
    private String meaning;         // 한국어 뜻

    @Field("is_completed")
    private boolean isCompleted;    // 해당 단어 학습 완료 여부
}
