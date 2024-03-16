package EBus.EBusback.domain.stop.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PinnedStopTimeResDto {
    private List<TimeResponseDto> ups;
    private List<TimeResponseDto> downs;

    @Builder
    public PinnedStopTimeResDto(List<TimeResponseDto> closeUps, List<TimeResponseDto> closeDowns){
        this.ups = closeUps;
        this.downs = closeDowns;
    }
}
