package software_capstone.backend.app.store.dto;

import lombok.Builder;
import lombok.Getter;
import software_capstone.backend.app.store.document.ShopItem;
import software_capstone.backend.app.store.document.category.ItemCategory;

@Getter
@Builder
public class ShopItemResponse {

    private String id;
    private String name;
    private int price;
    private int growthRate;
    private ItemCategory category;
    private String imageUrl;

    public static ShopItemResponse from(ShopItem shopItem) {
        return ShopItemResponse.builder()
                .id(shopItem.getId())
                .name(shopItem.getName())
                .price(shopItem.getPrice())
                .growthRate(shopItem.getGrowthRate())
                .category(shopItem.getCategory())
                .imageUrl(shopItem.getImageUrl())
                .build();
    }
}
