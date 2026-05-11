package software_capstone.backend.app.user.dto;

import lombok.Builder;
import software_capstone.backend.app.avocado.dto.AvocadoSummaryResponse;
import software_capstone.backend.app.avocado.dto.AvocadoInfoResponse;

import java.util.List;

@Builder
public record UserMyPageResponse(
        String userName,
        AvocadoInfoResponse currentAvocadoInfo,
        // TODO: 학습 기록 날짜 반환하기
        List<AvocadoSummaryResponse> avocadoArchiveList
) {
}
