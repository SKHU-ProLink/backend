package software_capstone.backend.app.abocado.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Level {
    ONE(100),
    TWO(250),
    THREE(450);

    // 다음 레벨로 가기 위한 경험치량, 임시 값으로 넣어둔 상태
    // TODO: 기획 확립 시 이 값을 변경해야 함
    private final int expToNextLevel;
}
