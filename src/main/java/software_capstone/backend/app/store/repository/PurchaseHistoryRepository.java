package software_capstone.backend.app.store.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import software_capstone.backend.app.store.document.PurchaseHistory;

public interface PurchaseHistoryRepository extends MongoRepository<PurchaseHistory, String> {
}
