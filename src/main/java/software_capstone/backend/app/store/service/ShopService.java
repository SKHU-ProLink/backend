package software_capstone.backend.app.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software_capstone.backend.app.inventory.service.InventoryService;
import software_capstone.backend.app.store.document.category.ItemCategory;
import software_capstone.backend.app.store.document.ShopItem;
import software_capstone.backend.app.store.dto.request.PurchaseRequest;
import software_capstone.backend.app.store.dto.response.PurchaseResponse;
import software_capstone.backend.app.store.dto.response.ShopItemResponse;
import software_capstone.backend.app.store.repository.ShopItemRepository;
import software_capstone.backend.app.user.service.UserService;
import software_capstone.backend.app.wallet.document.Wallet;
import software_capstone.backend.app.wallet.service.WalletService;
import software_capstone.backend.global.exception.ErrorMessage;
import software_capstone.backend.global.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopItemRepository shopItemRepository;
    private final WalletService walletService;
    private final InventoryService inventoryService;
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
        userService.findUserById(userId);

        ShopItem item = shopItemRepository.findByIdAndIsReleasedTrue(request.getItemId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ITEM_NOT_FOUND));

        walletService.deductCash(userId, item.getPrice());

        inventoryService.addItem(userId, item);

        Wallet wallet = walletService.findWalletByUserId(userId);
        return PurchaseResponse.of(item, wallet.getCash());
    }
}
