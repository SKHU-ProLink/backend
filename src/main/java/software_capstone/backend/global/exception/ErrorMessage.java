package software_capstone.backend.global.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    // User
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),

    // JWT
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("만료된 토큰입니다."),
    REFRESH_TOKEN_NOT_ALLOWED("리프레시 토큰으로는 접근할 수 없습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
