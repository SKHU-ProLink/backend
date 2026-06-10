package software_capstone.backend.app.learning.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Sentence {

    private String word;        // 해당 단어
    private String sentence;    // 영어 예문
    private String meaning;     // 한국어 해석
}
