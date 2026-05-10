package software_capstone.backend.global.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    // User
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    USER_ALREADY_ONBOARDED("이미 온보딩이 완료된 유저입니다."),
    USER_NOT_ONBOARDED("온보딩이 완료되지 않은 유저입니다."),

    // Learning
    LEARNING_SESSION_NOT_FOUND("오늘의 학습 세션을 찾을 수 없습니다."),
    SESSION_USER_MISMATCH("해당 학습 세션에 대한 권한이 없습니다."),
    ALREADY_COMPLETED_SESSION("이미 완료된 학습 세션입니다."),
    EMPTY_AUDIO_FILE("오디오 파일이 비어있습니다."),
    LANGCHAIN_SERVER_ERROR("AI 서버와의 통신 중 오류가 발생했습니다."),

    // JWT
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("만료된 토큰입니다."),
    REFRESH_TOKEN_NOT_ALLOWED("리프레시 토큰으로는 접근할 수 없습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
