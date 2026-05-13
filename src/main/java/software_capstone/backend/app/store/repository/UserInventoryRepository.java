package software_capstone.backend.app.store.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import software_capstone.backend.app.store.document.UserInventory;

import java.util.List;
import java.util.Optional;

public interface UserInventoryRepository extends MongoRepository<UserInventory, String> {
    Optional<UserInventory> findByUserIdAndItemId(String userId, String itemId);
    List<UserInventory> findByUserId(String userId);
}
