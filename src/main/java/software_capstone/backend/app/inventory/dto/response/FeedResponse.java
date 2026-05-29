package software_capstone.backend.app.inventory.dto.response;

import lombok.Builder;
import lombok.Getter;
import software_capstone.backend.app.inventory.document.UserInventory;

@Getter
@Builder
public class FeedResponse {
    private int remainingQuantity;
    private int currentExp;
    private String currentLevel;

    public static FeedResponse of(UserInventory inventory, Abocado abocado) {
        return FeedResponse.builder()
                .remainingQuantity(inventory.getQuantity())
                .currentExp(abocado.getExp())
                .currentLevel(abocado.getLevel().name())
                .build();
    }
}
