package software_capstone.backend.app.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software_capstone.backend.app.avocado.document.Avocado;
import software_capstone.backend.app.avocado.repository.AvocadoRepository;
import software_capstone.backend.app.inventory.dto.request.FeedRequest;
import software_capstone.backend.app.inventory.dto.response.FeedResponse;
import software_capstone.backend.app.store.document.ShopItem;
import software_capstone.backend.app.inventory.document.UserInventory;
import software_capstone.backend.app.inventory.dto.response.InventoryResponse;
import software_capstone.backend.app.inventory.repository.UserInventoryRepository;
import software_capstone.backend.app.user.service.UserService;
import software_capstone.backend.global.exception.ErrorMessage;
import software_capstone.backend.global.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final UserInventoryRepository userInventoryRepository;
    private final AvocadoRepository avocadoRepository;
    private final UserService userService;

    @Transactional
    public void addItem(String userId, ShopItem item) {
        UserInventory inventory = userInventoryRepository
                .findByUserIdAndItemId(userId, item.getId())
                .orElseGet(() -> UserInventory.builder()
                        .userId(userId)
                        .itemId(item.getId())
                        .itemName(item.getName())
                        .category(item.getCategory())
                        .grade(item.getGrade())
                        .build());
        inventory.increaseQuantity();
        userInventoryRepository.save(inventory);
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> getInventory(String userId) {
        userService.validateUserExists(userId);

        return userInventoryRepository.findByUserId(userId).stream()
                .map(InventoryResponse::from)
                .toList();
    }

    @Transactional
    public FeedResponse feedItem(String userId, FeedRequest request) {
        userService.validateUserExists(userId);

        UserInventory inventory = userInventoryRepository.findById(request.getInventoryId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ITEM_NOT_FOUND));

        inventory.decreaseQuantity();
        userInventoryRepository.save(inventory);

        Avocado avocado = avocadoRepository.findCurrentAvocado(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.AVOCADO_NOT_FOUND));

        boolean isLevelUp = avocado.increaseExpAndCheckLevelUp(inventory.getGrade().getXpOnFeed());
        avocadoRepository.save(avocado);

        return FeedResponse.of(inventory, avocado, isLevelUp);
    }
}
