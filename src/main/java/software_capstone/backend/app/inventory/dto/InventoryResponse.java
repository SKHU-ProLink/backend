package software_capstone.backend.app.inventory.dto;

import lombok.Builder;
import lombok.Getter;
import software_capstone.backend.app.inventory.document.UserInventory;
import software_capstone.backend.app.store.document.category.ItemCategory;
import software_capstone.backend.app.store.document.category.ItemGrade;

@Getter
@Builder
public class InventoryResponse {
    private String inventoryId;
    private String itemId;
    private String itemName;
    private ItemCategory category;
    private ItemGrade grade;
    private int quantity;

    public static InventoryResponse from(UserInventory inventory) {
        return InventoryResponse.builder()
                .inventoryId(inventory.getId())
                .itemId(inventory.getItemId())
                .itemName(inventory.getItemName())
                .category(inventory.getCategory())
                .grade(inventory.getGrade())
                .quantity(inventory.getQuantity())
                .build();
    }
}
