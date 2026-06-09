package software_capstone.backend.app.avocado.dto;

import jakarta.validation.constraints.NotBlank;

public record AvocadoCreateRequest(
        @NotBlank String name
) {
}
