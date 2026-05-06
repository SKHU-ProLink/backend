package software_capstone.backend.app.avocado.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import software_capstone.backend.app.avocado.document.Avocado;

import java.util.List;
import java.util.Optional;

public interface AvocadoRepository extends MongoRepository<Avocado, String> {
    @Query("{ 'userId': ?0, 'isActive': false }")
    List<Avocado> findCompletedAvocados(String userId);

    @Query("{ 'userId': ?0, 'isActive': true }")
    Optional<Avocado> findCurrentAvocado(String userId);
}
