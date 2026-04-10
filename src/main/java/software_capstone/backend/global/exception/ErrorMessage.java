package software_capstone.backend.global.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    // User
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
