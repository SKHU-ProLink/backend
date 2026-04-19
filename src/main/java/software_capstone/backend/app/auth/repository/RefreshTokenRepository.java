package software_capstone.backend.app.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import software_capstone.backend.app.auth.document.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserIdAndDeviceInfo(String userId, String deviceInfo) ;

    void deleteByUserId(String userId);

    void deleteByToken(String token);
}
