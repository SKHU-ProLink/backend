package software_capstone.backend.app.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software_capstone.backend.app.store.document.ShopItem;
import software_capstone.backend.app.inventory.document.UserInventory;
import software_capstone.backend.app.inventory.dto.InventoryResponse;
import software_capstone.backend.app.inventory.repository.ShopItemRepository;
import software_capstone.backend.app.store.repository.UserInventoryRepository;
import software_capstone.backend.app.user.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final UserInventoryRepository userInventoryRepository;
    private final ShopItemRepository shopItemRepository;
    private final UserService userService;

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

    @Transactional(readOnly = true)
    public List<InventoryResponse> getInventory(String userId) {
        userService.findUserById(userId);

        List<UserInventory> inventories = userInventoryRepository.findByUserId(userId);

        List<String> itemIds = inventories.stream()
                .map(UserInventory::getItemId)
                .toList();

        Map<String, ShopItem> itemMap = shopItemRepository.findByIdIn(itemIds).stream()
                .collect(Collectors.toMap(ShopItem::getId, item -> item));

        return inventories.stream()
                .filter(inventory -> itemMap.containsKey(inventory.getItemId()))
                .map(inventory -> InventoryResponse.of(inventory, itemMap.get(inventory.getItemId())))
                .toList();
    }
}
