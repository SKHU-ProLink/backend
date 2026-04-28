package software_capstone.backend.app.abocado.dto;

import lombok.Builder;

@Builder
public record AbocadoInfoResponse(
        String id,
        String name,
        int level,
        int currentExp,
        int expToNextLevel
) {
}
