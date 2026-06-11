package software_capstone.backend.app.inventory.dto.response;

import lombok.Builder;
import lombok.Getter;
import software_capstone.backend.app.avocado.document.Avocado;
import software_capstone.backend.app.avocado.dto.AvocadoExpGrantResponse;
import software_capstone.backend.app.inventory.document.UserInventory;

@Getter
@Builder
public class FeedResponse {
    private int remainingQuantity;
    private int currentExp;
    private int currentLevel;
    private boolean isLevelUp;

    public static FeedResponse of(UserInventory inventory, AvocadoExpGrantResponse response) {
        return FeedResponse.builder()
                .remainingQuantity(inventory.getQuantity())
                .currentExp(response.currentExp())
                .currentLevel(response.currentLevel())
                .isLevelUp(response.isLevelUp())
                .build();
    }
}
