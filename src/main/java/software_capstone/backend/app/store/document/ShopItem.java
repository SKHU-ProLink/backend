package software_capstone.backend.app.store.document;

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
@Document(collection = "shop_items")
public class ShopItem extends BaseEntity {

    private String name;
    private int price;
    private int growthRate;
    private int xpOnFeed;
    private ItemCategory category;
    private ItemGrade grade;
    private boolean isReleased;
}
