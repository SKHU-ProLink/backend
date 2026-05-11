package software_capstone.backend.app.avocado.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import software_capstone.backend.global.document.BaseEntity;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "avocados")
public class Avocado extends BaseEntity {

    @Indexed
    private String userId;

    private String name;

    @Builder.Default
    private Level level = Level.ONE; // 생성 시에 초기 레벨은 1

    @Builder.Default
    private int exp = 0; // 생성 시에 기본 경험치는 없음

    @Builder.Default
    private boolean isActive = true; // 현재 성장시키고 있는 객체인지 판독
}
