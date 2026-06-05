package software_capstone.backend.app.wallet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software_capstone.backend.app.user.service.UserService;
import software_capstone.backend.app.wallet.document.Wallet;
import software_capstone.backend.app.wallet.dto.WalletResponse;
import software_capstone.backend.app.wallet.repository.WalletRepository;
import software_capstone.backend.global.exception.ErrorMessage;
import software_capstone.backend.global.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final UserService userService;

    public Wallet findWalletByUserId(String userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.WALLET_NOT_FOUND));
    }

    public void deductCash(String userId, int amount) {
        Wallet wallet = findWalletByUserId(userId);
        wallet.deductCash(amount);
        walletRepository.save(wallet);
    }

    public void chargeCash(String userId, int amount) {
        Wallet wallet = findWalletByUserId(userId);
        wallet.chargeCash(amount);
        walletRepository.save(wallet);
    }

    public void chargeGold(String userId, int amount) {
        Wallet wallet = findWalletByUserId(userId);
        wallet.chargeGold(amount);
        walletRepository.save(wallet);
    }

    public WalletResponse getWallet(String userId) {
        userService.validateUserExists(userId);
        Wallet wallet = findWalletByUserId(userId);
        return WalletResponse.from(wallet);
    }
}
