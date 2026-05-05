package software_capstone.backend.app.avocado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import software_capstone.backend.app.user.document.Difficulty;

public record AvocadoOnboardingRequest(
        @NotBlank String name,
        @NotNull Difficulty difficulty
) {
}
