package software_capstone.backend.app.store.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import software_capstone.backend.app.store.document.category.ItemCategory;
import software_capstone.backend.app.store.document.ShopItem;

import java.util.List;

public interface ShopItemRepository extends MongoRepository<ShopItem, String> {

    List<ShopItem> findByIsReleasedTrue();

    List<ShopItem> findByCategoryAndIsReleasedTrue(ItemCategory category);
}
