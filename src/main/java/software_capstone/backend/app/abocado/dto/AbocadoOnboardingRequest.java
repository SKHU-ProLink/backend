package software_capstone.backend.app.abocado.dto;

import software_capstone.backend.app.abocado.document.Difficulty;

public record AbocadoOnboardingRequest(
        String name,
        Difficulty difficulty
) {
}
