package software_capstone.backend.app.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import software_capstone.backend.app.auth.document.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserIdAndDeviceInfo(String userId, String deviceInfo) ;

    void deleteByUserId(String userId);         // 추후 회원탈퇴 시 사용할 예정

    void deleteByToken(String token);
}
