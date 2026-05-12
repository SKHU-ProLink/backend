package software_capstone.backend.app.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software_capstone.backend.app.store.document.UserInventory;
import software_capstone.backend.app.store.repository.UserInventoryRepository;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final UserInventoryRepository userInventoryRepository;

    public void addItem(String userId, String itemId) {
        UserInventory inventory = userInventoryRepository
                .findByUserIdAndItemId(userId, itemId)
                .orElseGet(() -> UserInventory.builder()
                        .userId(userId)
                        .itemId(itemId)
                        .build());
        inventory.increaseQuantity();
        userInventoryRepository.save(inventory);
    }
}
