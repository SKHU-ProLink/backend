package software_capstone.backend.app.avocado.dto;

import lombok.Builder;

@Builder
public record AvocadoSummaryResponse(
        String id,
        String name
) {
}
