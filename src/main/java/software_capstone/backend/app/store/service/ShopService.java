package software_capstone.backend.app.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software_capstone.backend.app.store.document.category.ItemCategory;
import software_capstone.backend.app.store.document.ShopItem;
import software_capstone.backend.app.store.dto.ShopItemResponse;
import software_capstone.backend.app.store.repository.ShopItemRepository;
import software_capstone.backend.app.user.repository.UserRepository;
import software_capstone.backend.global.exception.ErrorMessage;
import software_capstone.backend.global.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopItemRepository shopItemRepository;
    private final UserRepository userRepository;

    public List<ShopItemResponse> getItems(String userId, ItemCategory category) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

        List<ShopItem> items = (category == null)
                ? shopItemRepository.findByIsReleasedTrue()
                : shopItemRepository.findByCategoryAndIsReleasedTrue(category);

        return items.stream()
                .map(ShopItemResponse::from)
                .toList();
    }
}
