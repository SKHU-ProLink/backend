package software_capstone.backend.app.auth.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        boolean isNewUser
) {}
