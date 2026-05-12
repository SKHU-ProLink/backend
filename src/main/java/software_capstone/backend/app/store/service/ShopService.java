package software_capstone.backend.app.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software_capstone.backend.app.store.document.PurchaseHistory;
import software_capstone.backend.app.store.document.UserInventory;
import software_capstone.backend.app.store.document.category.ItemCategory;
import software_capstone.backend.app.store.document.ShopItem;
import software_capstone.backend.app.store.dto.request.PurchaseRequest;
import software_capstone.backend.app.store.dto.response.InventoryResponse;
import software_capstone.backend.app.store.dto.response.PurchaseResponse;
import software_capstone.backend.app.store.dto.response.ShopItemResponse;
import software_capstone.backend.app.store.repository.PurchaseHistoryRepository;
import software_capstone.backend.app.store.repository.ShopItemRepository;
import software_capstone.backend.app.store.repository.UserInventoryRepository;
import software_capstone.backend.app.user.document.User;
import software_capstone.backend.app.user.service.UserService;
import software_capstone.backend.global.exception.ErrorMessage;
import software_capstone.backend.global.exception.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopItemRepository shopItemRepository;
    private final InventoryService inventoryService;
    private final PurchaseHistoryService purchaseHistoryService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<ShopItemResponse> getItems(String userId, ItemCategory category) {
        userService.findUserById(userId);

        List<ShopItem> items = (category == null)
                ? shopItemRepository.findByIsReleasedTrue()
                : shopItemRepository.findByCategoryAndIsReleasedTrue(category);

        return items.stream()
                .map(ShopItemResponse::from)
                .toList();
    }

    @Transactional
    public PurchaseResponse purchaseItem(String userId, PurchaseRequest request) {
        User user = userService.findUserById(userId);

        ShopItem item = shopItemRepository.findByIdAndIsReleasedTrue(request.getItemId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ITEM_NOT_FOUND));

        user.deductCash(item.getPrice());
        userService.save(user);

        inventoryService.addItem(userId, item.getId());
        purchaseHistoryService.save(userId, item);

        return PurchaseResponse.of(item, user.getCash());
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
