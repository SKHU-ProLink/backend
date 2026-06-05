package software_capstone.backend.app.wallet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software_capstone.backend.app.auth.jwt.TokenProvider;
import software_capstone.backend.app.wallet.dto.WalletResponse;
import software_capstone.backend.app.wallet.service.WalletService;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController implements WalletControllerDocs {
    private final WalletService walletService;

    @Override
    @GetMapping
    public ResponseEntity<WalletResponse> getWallet(
            @AuthenticationPrincipal TokenProvider.AuthUser authUser
    ) {
        return ResponseEntity.ok(walletService.getWallet(authUser.userId()));
    }
}
