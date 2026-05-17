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

    public void addItem(String userId, ShopItem item) {
        UserInventory inventory = userInventoryRepository
                .findByUserIdAndItemId(userId, item.getId())
                .orElseGet(() -> UserInventory.builder()
                        .userId(userId)
                        .itemId(item.getId())
                        .itemName(item.getName())
                        .category(item.getCategory())
                        .grade(item.getGrade())
                        .quantity(0)
                        .build());
        inventory.increaseQuantity();
        userInventoryRepository.save(inventory);
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> getInventory(String userId) {
        userService.findUserById(userId);

        return userInventoryRepository.findByUserId(userId).stream()
                .map(InventoryResponse::from)
                .toList();
    }
}
