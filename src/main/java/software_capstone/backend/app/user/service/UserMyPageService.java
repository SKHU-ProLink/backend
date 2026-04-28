package software_capstone.backend.app.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software_capstone.backend.app.abocado.document.Abocado;
import software_capstone.backend.app.abocado.document.AbocadoSummaryResponse;
import software_capstone.backend.app.abocado.dto.AbocadoInfoResponse;
import software_capstone.backend.app.abocado.service.AbocadoService;
import software_capstone.backend.app.user.document.User;
import software_capstone.backend.app.user.dto.UserMyPageResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMyPageService {

    private final UserService userService;
    private final AbocadoService abocadoService;

    @Transactional(readOnly = true)
    public UserMyPageResponse getUserMyPage(String userId) {
        User user = userService.findUserById(userId);
        Abocado abocado = abocadoService.findAbocadoByUserId(userId);
        List<Abocado> completedAbocados = abocadoService.getCompletedAbocados(userId);

        return UserMyPageResponse.builder()
                .userName(user.getName())
                .currentAbocadoInfo(getCurrentAbocadoInfo(abocado))
                // TODO: 학습 기록 날짜 반환하기
                .abocadoArchiveList(getAbocadoSummary(completedAbocados))
                .build();
    }

    private AbocadoInfoResponse getCurrentAbocadoInfo(Abocado abocado) {
        return AbocadoInfoResponse.builder()
                .id(abocado.getId())
                .name(abocado.getName())
                .level(abocado.getLevel().getLevelToInt())
                .currentExp(abocado.getExp())
                .expToNextLevel(abocado.getLevel().getExpToNextLevel())
                .build();
    }

    private List<AbocadoSummaryResponse> getAbocadoSummary(List<Abocado> abocados) {
        return abocados.stream()
            .map( abocado -> AbocadoSummaryResponse.builder()
                    .id(abocado.getId())
                    .name(abocado.getName()).build()
            )
            .toList();
    }
}
