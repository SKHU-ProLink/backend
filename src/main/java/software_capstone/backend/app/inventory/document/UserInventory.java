package software_capstone.backend.app.inventory.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import software_capstone.backend.app.store.document.category.ItemCategory;
import software_capstone.backend.app.store.document.category.ItemGrade;
import software_capstone.backend.global.document.BaseEntity;
import software_capstone.backend.global.exception.BadRequestException;
import software_capstone.backend.global.exception.ErrorMessage;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "user_inventories")
public class UserInventory extends BaseEntity {

    @Indexed
    private String userId;
    private String itemId;
    private String itemName;
    private ItemCategory category;
    private ItemGrade grade;

    @Builder.Default
    private int quantity = 0;

    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        if (this.quantity <= 0) {
            throw new BadRequestException(ErrorMessage.INSUFFICIENT_ITEM);
        }
        this.quantity--;
    }
}
