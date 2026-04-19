package software_capstone.backend.app.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import software_capstone.backend.app.auth.document.OAuthProvider;
import software_capstone.backend.app.auth.document.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByOauthProviderAndOauthId(OAuthProvider oauthProvider, String oauthId);
}
