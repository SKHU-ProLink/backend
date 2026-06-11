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

    public boolean increaseExpAndCheckLevelUp(int exp) {
        this.exp += exp;
        if (this.exp >= this.level.getExpToNextLevel()) {
            this.exp = this.exp - this.level.getExpToNextLevel(); // 초과된 경험치는 다음 레벨로 경험치 이월
            this.level = this.level.nextLevel();
            if (this.level.isMaxLevel()) {
                this.isActive = false; // MAX 레벨이라면 해당 캐릭터는 비활성화
            }
            return true; // 레벨업했을 시에는 true 반환
        }
        return false; // 레벨업 없이 경험치만 추가됐을 시에는 false 반환
    }
}
