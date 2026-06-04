package software_capstone.backend.app.inventory.dto.response;

import lombok.Builder;
import lombok.Getter;
import software_capstone.backend.app.avocado.document.Avocado;
import software_capstone.backend.app.inventory.document.UserInventory;

@Getter
@Builder
public class FeedResponse {
    private int remainingQuantity;
    private int currentExp;
    private String currentLevel;

    public static FeedResponse of(UserInventory inventory, Avocado avocado) {
        return FeedResponse.builder()
                .remainingQuantity(inventory.getQuantity())
                .currentExp(avocado.getExp())
                .currentLevel(avocado.getLevel().name())
                .build();
    }
}
