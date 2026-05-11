package software_capstone.backend.app.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software_capstone.backend.app.store.document.category.ItemCategory;
import software_capstone.backend.app.store.document.ShopItem;
import software_capstone.backend.app.store.dto.response.ShopItemResponse;
import software_capstone.backend.app.store.repository.ShopItemRepository;
import software_capstone.backend.app.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopItemRepository shopItemRepository;
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
}
