package EBus.EBusback.domain.stop.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class WholeTimetableResDto {
    private List<TimeResponseDto> ups;
    private List<TimeResponseDto> downs;
    private TimeResponseDto closestUp;
    private TimeResponseDto closestDown;

    @Builder
    public WholeTimetableResDto (List<TimeResponseDto> ups, List<TimeResponseDto> downs, TimeResponseDto closestUp, TimeResponseDto closestDown){
        this.ups = ups;
        this.downs = downs;
        this.closestUp = closestUp;
        this.closestDown = closestDown;
    }
}
