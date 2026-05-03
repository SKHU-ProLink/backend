package software_capstone.backend.app.auth.dto;

public record TokenResponse(
        String userId,
        String accessToken,
        String refreshToken,
        boolean isNewUser
) {}
