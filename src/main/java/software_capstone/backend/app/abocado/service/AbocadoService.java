package software_capstone.backend.app.abocado.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software_capstone.backend.app.abocado.document.Abocado;
import software_capstone.backend.app.abocado.dto.AbocadoOnboardingRequest;
import software_capstone.backend.app.abocado.repository.AbocadoRepository;
import software_capstone.backend.app.user.document.User;
import software_capstone.backend.app.user.repository.UserRepository;
import software_capstone.backend.app.user.service.UserService;

@Service
@RequiredArgsConstructor
public class AbocadoService {

    private final UserService userService;
    private final AbocadoRepository abocadoRepository;
    private final UserRepository userRepository;

    // 메서드가 2개 이상의 컬렉션을 다루고 있기에, @Transaction 필요
    @Transactional
    public void onBoarding(
            String userId,
            AbocadoOnboardingRequest request
    ) {
        abocadoRepository.save(
                Abocado.builder()
                        .userId(userId)
                        .name(request.name())
                        .build());

        User user = userService.findUserById(userId);
        user.updateDifficulty(request.difficulty());
        userRepository.save(user); // MongoDB는 JPA와 달리 영속성 컨텍스트가 없기에, 저장을 반영하려면 save 메서드 필요
    }
}
