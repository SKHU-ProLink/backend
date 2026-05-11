package software_capstone.backend.app.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import software_capstone.backend.app.store.document.ShopItem;

@Getter
@Builder
public class PurchaseResponse {
    private String itemId;
    private String itemName;
    private int remainingCash;

    public static PurchaseResponse of(ShopItem item, int remainingCash) {
        return PurchaseResponse.builder()
                .itemId(item.getId())
                .itemName(item.getName())
                .remainingCash(remainingCash)
                .build();
    }
}
