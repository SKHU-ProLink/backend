package software_capstone.backend.app.learning.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import software_capstone.backend.app.learning.document.LearningSession;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface LearningSessionRepository extends MongoRepository<LearningSession, String> {

    Optional<LearningSession> findByUserIdAndDate(String userId, LocalDate date);
}
