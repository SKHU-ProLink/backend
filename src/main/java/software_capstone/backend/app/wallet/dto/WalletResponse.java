package software_capstone.backend.app.wallet.dto;

import lombok.Builder;
import lombok.Getter;
import software_capstone.backend.app.wallet.document.Wallet;

@Getter
@Builder
public class WalletResponse {
    private int cash;
    private int gold;

    public static WalletResponse from(Wallet wallet) {
        return WalletResponse.builder()
                .cash(wallet.getCash())
                .gold(wallet.getGold())
                .build();
    }
}
