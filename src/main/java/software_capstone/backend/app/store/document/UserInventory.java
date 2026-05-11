package software_capstone.backend.app.store.document;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import software_capstone.backend.global.document.BaseEntity;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "user_inventories")
public class UserInventory extends BaseEntity {
    @Indexed
    private String userId;
    private String itemId;
    @Builder.Default
    private int quantity = 0;

    public void increaseQuantity() {
        this.quantity++;
    }
}
