package software_capstone.backend.app.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software_capstone.backend.app.store.document.PurchaseHistory;
import software_capstone.backend.app.store.document.ShopItem;
import software_capstone.backend.app.store.repository.PurchaseHistoryRepository;

@Service
@RequiredArgsConstructor
public class PurchaseHistoryService {
    private final PurchaseHistoryRepository purchaseHistoryRepository;

    public void save(String userId, ShopItem item) {
        purchaseHistoryRepository.save(PurchaseHistory.builder()
                .userId(userId)
                .itemId(item.getId())
                .price(item.getPrice())
                .build());
    }
}
