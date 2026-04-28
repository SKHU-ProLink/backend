package software_capstone.backend.app.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software_capstone.backend.app.avocado.document.Avocado;
import software_capstone.backend.app.avocado.dto.AvocadoSummaryResponse;
import software_capstone.backend.app.avocado.dto.AvocadoInfoResponse;
import software_capstone.backend.app.avocado.service.AvocadoService;
import software_capstone.backend.app.user.document.User;
import software_capstone.backend.app.user.dto.UserMyPageResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMyPageService {

    private final UserService userService;
    private final AvocadoService avocadoService;

    @Transactional(readOnly = true)
    public UserMyPageResponse getUserMyPage(String userId) {
        User user = userService.findUserById(userId);
        Avocado avocado = avocadoService.findAvocadoByUserId(userId);
        List<Avocado> completedAvocados = avocadoService.getCompletedAvocados(userId);

        return UserMyPageResponse.builder()
                .userName(user.getName())
                .currentAvocadoInfo(getCurrentAvocadoInfo(avocado))
                // TODO: 학습 기록 날짜 반환하기
                .avocadoArchiveList(getAvocadoSummary(completedAvocados))
                .build();
    }

    private AvocadoInfoResponse getCurrentAvocadoInfo(Avocado avocado) {
        return AvocadoInfoResponse.builder()
                .id(avocado.getId())
                .name(avocado.getName())
                .level(avocado.getLevel().getLevelToInt())
                .currentExp(avocado.getExp())
                .expToNextLevel(avocado.getLevel().getExpToNextLevel())
                .build();
    }

    private List<AvocadoSummaryResponse> getAvocadoSummary(List<Avocado> avocados) {
        return avocados.stream()
            .map( avocado -> AvocadoSummaryResponse.builder()
                    .id(avocado.getId())
                    .name(avocado.getName()).build()
            )
            .toList();
    }
}
