package software_capstone.backend.app.abocado.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import software_capstone.backend.app.abocado.document.Abocado;

public interface AbocadoRepository extends MongoRepository<Abocado, String> {
}
