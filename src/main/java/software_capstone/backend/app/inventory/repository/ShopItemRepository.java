package software_capstone.backend.app.inventory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import software_capstone.backend.app.store.document.category.ItemCategory;
import software_capstone.backend.app.store.document.ShopItem;

import java.util.List;
import java.util.Optional;

public interface ShopItemRepository extends MongoRepository<ShopItem, String> {

    // 전체 출시 아이템 조회
    List<ShopItem> findByIsReleasedTrue();
    // 카테고리별 출시 아이템 조회
    List<ShopItem> findByCategoryAndIsReleasedTrue(ItemCategory category);
    // 단건 출시 아이템 조회 (구새 시 사용)
    Optional<ShopItem> findByIdAndIsReleasedTrue(String id);
    // id 한 번에 조회
    List<ShopItem> findByIdIn(List<String> ids);
}
