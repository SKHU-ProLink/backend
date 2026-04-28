package software_capstone.backend.app.avocado.model;

import software_capstone.backend.app.avocado.document.Avocado;
import software_capstone.backend.app.avocado.dto.AvocadoInfoResponse;
import software_capstone.backend.app.avocado.dto.AvocadoSummaryResponse;

import java.util.List;

public class AvocadoMapper {

    public static AvocadoInfoResponse toInfoResponse(Avocado avocado) {
        return AvocadoInfoResponse.builder()
                .id(avocado.getId())
                .name(avocado.getName())
                .level(avocado.getLevel().getLevelToInt())
                .currentExp(avocado.getExp())
                .expToNextLevel(avocado.getLevel().getExpToNextLevel())
                .build();
    }

    public static AvocadoSummaryResponse toSummaryResponse(Avocado avocado) {
        return AvocadoSummaryResponse.builder()
                .id(avocado.getId())
                .name(avocado.getName())
                .build();
    }

    public static List<AvocadoSummaryResponse> toSummaryResponseList(List<Avocado> avocados) {
        return avocados.stream()
                .map(AvocadoMapper::toSummaryResponse)
                .toList();
    }
}
