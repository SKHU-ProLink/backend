package software_capstone.backend.app.avocado.dto;

import lombok.Builder;

@Builder
public record AvocadoInfoResponse(
        String id,
        String name,
        int level,
        int currentExp,
        int expToNextLevel
) {
}
