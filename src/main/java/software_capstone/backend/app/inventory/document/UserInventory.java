package software_capstone.backend.app.inventory.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import software_capstone.backend.app.store.document.category.ItemCategory;
import software_capstone.backend.app.store.document.category.ItemGrade;
import software_capstone.backend.global.document.BaseEntity;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "user_inventories")
public class UserInventory extends BaseEntity {
    private String userId;
    private String itemId;
    private String itemName;
    private ItemCategory category;
    private ItemGrade grade;
    private int quantity;

    public void increaseQuantity() {
        this.quantity++;
    }
}
