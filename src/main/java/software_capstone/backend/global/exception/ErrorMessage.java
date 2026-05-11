package software_capstone.backend.global.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    // User
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    USER_ALREADY_ONBOARDED("이미 온보딩이 완료된 유저입니다."),

    // JWT
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("만료된 토큰입니다."),
    REFRESH_TOKEN_NOT_ALLOWED("리프레시 토큰으로는 접근할 수 없습니다."),

    // Avocado
    CURRENT_ACTIVE_AVOCADO_NOT_FOUND("현재 활성화중인 아보카도를 찾을 수 없습니다."),

    // store
    INSUFFICIENT_CASH("캐시가 부족합니다.");
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
