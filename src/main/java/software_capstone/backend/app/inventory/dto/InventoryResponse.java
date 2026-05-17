package software_capstone.backend.app.inventory.dto;

import lombok.Builder;
import lombok.Getter;
import software_capstone.backend.app.store.document.ShopItem;
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

    public static InventoryResponse of(UserInventory inventory, ShopItem item) {
        return InventoryResponse.builder()
                .inventoryId(inventory.getId())
                .itemId(item.getId())
                .itemName(item.getName())
                .category(item.getCategory())
                .grade(item.getGrade())
                .quantity(inventory.getQuantity())
                .build();
    }
}
