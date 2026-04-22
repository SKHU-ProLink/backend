package software_capstone.backend.app.auth.dto;

public record LoginRequest(
        String code,
        String deviceInfo
) {}
