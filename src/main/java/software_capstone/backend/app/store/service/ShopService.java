package software_capstone.backend.app.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software_capstone.backend.app.store.document.category.ItemCategory;
import software_capstone.backend.app.store.document.ShopItem;
import software_capstone.backend.app.store.repository.ShopItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopItemRepository shopItemRepository;

    public List<ShopItem> getItems(ItemCategory category) {
        if (category == null) {
            return shopItemRepository.findByIsReleasedTrue();
        }
        return shopItemRepository.findByCategoryAndIsReleasedTrue(category);
    }
}
