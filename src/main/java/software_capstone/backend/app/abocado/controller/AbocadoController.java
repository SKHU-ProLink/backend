package software_capstone.backend.app.abocado.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software_capstone.backend.app.abocado.dto.AbocadoOnboardingRequest;
import software_capstone.backend.app.abocado.service.AbocadoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/abocado")
public class AbocadoController {

    private final AbocadoService abocadoService;

    @PostMapping("/onboarding")
    public ResponseEntity<Void> onBoarding(
            @AuthenticationPrincipal String userId,
            AbocadoOnboardingRequest request
    ) {
        abocadoService.onBoarding(userId, request);
        return ResponseEntity.ok().build();
    }
}
