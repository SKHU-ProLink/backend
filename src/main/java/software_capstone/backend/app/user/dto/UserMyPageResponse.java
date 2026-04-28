package software_capstone.backend.app.user.dto;

import lombok.Builder;
import software_capstone.backend.app.abocado.document.AbocadoSummaryResponse;
import software_capstone.backend.app.abocado.dto.AbocadoInfoResponse;

import java.util.List;

@Builder
public record UserMyPageResponse(
        String userName,
        AbocadoInfoResponse currentAbocadoInfo,
        // TODO: 학습 기록 날짜 반환하기
        List<AbocadoSummaryResponse> abocadoArchiveList
) {
}
