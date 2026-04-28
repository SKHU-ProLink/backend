package software_capstone.backend.app.abocado.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import software_capstone.backend.app.abocado.document.Abocado;

import java.util.List;
import java.util.Optional;

public interface AbocadoRepository extends MongoRepository<Abocado, String> {
    @Query("{ 'userId': ?0, 'isActive': false }")
    List<Abocado> findCompletedAbocados(String userId);

    @Query("{ 'userId': ?0, 'isActive': true }")
    Optional<Abocado> findCurrentAbocado(String userId);
}
