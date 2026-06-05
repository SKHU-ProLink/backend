package software_capstone.backend.app.wallet.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import software_capstone.backend.global.document.BaseEntity;
import software_capstone.backend.global.exception.BadRequestException;
import software_capstone.backend.global.exception.ErrorMessage;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "wallets")
public class Wallet extends BaseEntity {

    @Indexed
    private String userId;
    @Builder.Default
    private int cash = 0;
    @Builder.Default
    private int gold = 0;

    public void chargeCash(int amount) {
        this.cash += amount;
    }

    public void deductCash(int amount) {
        if (this.cash < amount) {
            throw new BadRequestException(ErrorMessage.INSUFFICIENT_CASH);
        }
        this.cash -= amount;
    }

    public void chargeGold(int amount) {
        this.gold += amount;
    }

    public void deductGold(int amount) {
        if (this.gold < amount) {
            throw new BadRequestException(ErrorMessage.INSUFFICIENT_GOLD);
        }
        this.gold -= amount;
    }
}
