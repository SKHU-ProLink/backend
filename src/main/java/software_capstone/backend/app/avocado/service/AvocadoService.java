package software_capstone.backend.app.avocado.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software_capstone.backend.app.avocado.document.Avocado;
import software_capstone.backend.app.avocado.dto.AvocadoCreateRequest;
import software_capstone.backend.app.avocado.dto.AvocadoExpGrantResponse;
import software_capstone.backend.app.avocado.dto.AvocadoOnboardingRequest;
import software_capstone.backend.app.avocado.repository.AvocadoRepository;
import software_capstone.backend.app.user.document.User;
import software_capstone.backend.app.user.repository.UserRepository;
import software_capstone.backend.app.user.service.UserService;
import software_capstone.backend.global.exception.BadRequestException;
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

    public void createNewAvocado(
            String userId,
            AvocadoCreateRequest request
    ) {
        userService.validateUserExists(userId);
        checkIfActiveAvocadoExists(userId);

        avocadoRepository.save(
                Avocado.builder()
                        .userId(userId)
                        .name(request.name())
                        .build()
        );
    }

    public Avocado findAvocadoByUserId(String userId) {
        return avocadoRepository.findCurrentAvocado(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.CURRENT_ACTIVE_AVOCADO_NOT_FOUND));
    }

    public List<Avocado> getCompletedAvocados(String userId) {
        return avocadoRepository.findCompletedAvocados(userId);
    }

    private void checkIfActiveAvocadoExists(String userId) {
        if (avocadoRepository.findCurrentAvocado(userId).isPresent()) {
            throw new BadRequestException(ErrorMessage.ACTIVE_AVOCADO_ALREADY_EXISTS);
        }
    }

    @Transactional
    public AvocadoExpGrantResponse grantExp(String userId, int exp) {
        Avocado avocado = findAvocadoByUserId(userId);
        boolean isLevelUp = avocado.increaseExpAndCheckLevelUp(exp);

        // 레벨업 이후 현재 경험치량, MAX 레벨에 도달했다면 경험치 값을 -1로 반환
        int currentExp = avocado.getLevel().isMaxLevel() ? -1 : avocado.getExp();
        avocadoRepository.save(avocado);

        return AvocadoExpGrantResponse.builder()
                .isLevelUp(isLevelUp)
                .currentLevel(avocado.getLevel().getLevelToInt())
                .currentExp(currentExp)
                .build();
    }
}
