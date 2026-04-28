package software_capstone.backend.app.avocado.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software_capstone.backend.app.avocado.document.Avocado;
import software_capstone.backend.app.avocado.dto.AvocadoOnboardingRequest;
import software_capstone.backend.app.avocado.repository.AvocadoRepository;
import software_capstone.backend.app.user.document.User;
import software_capstone.backend.app.user.repository.UserRepository;
import software_capstone.backend.app.user.service.UserService;
import software_capstone.backend.global.exception.ErrorMessage;
import software_capstone.backend.global.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvocadoService {

    private final UserService userService;
    private final AvocadoRepository avocadoRepository;
    private final UserRepository userRepository;

    // 메서드가 2개 이상의 컬렉션을 다루고 있기에, @Transaction 필요
    @Transactional
    public void onBoarding(
            String userId,
            AvocadoOnboardingRequest request
    ) {
        avocadoRepository.save(
                Avocado.builder()
                        .userId(userId)
                        .name(request.name())
                        .build());

        User user = userService.findUserById(userId);
        user.completeOnboarding(request.difficulty());
        userRepository.save(user); // MongoDB는 JPA와 달리 영속성 컨텍스트가 없기에, 저장을 반영하려면 save 메서드 필요
    }

    public Avocado findAvocadoByUserId(String userId) {
        return avocadoRepository.findCurrentAvocado(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.CURRENT_ACTIVE_AVOCADO_NOT_FOUND));
    }

    public List<Avocado> getCompletedAvocados(String userId) {
        return avocadoRepository.findCompletedAvocados(userId);
    }
}
