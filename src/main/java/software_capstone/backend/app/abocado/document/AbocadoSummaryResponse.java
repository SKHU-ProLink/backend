package software_capstone.backend.app.abocado.document;

import lombok.Builder;

@Builder
public record AbocadoSummaryResponse(
        String id,
        String name
) {
}
