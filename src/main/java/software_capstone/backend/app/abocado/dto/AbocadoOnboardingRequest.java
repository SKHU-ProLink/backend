package software_capstone.backend.app.abocado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import software_capstone.backend.app.abocado.document.Difficulty;

public record AbocadoOnboardingRequest(
        @NotBlank String name,
        @NotNull Difficulty difficulty
) {
}
