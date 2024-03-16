package EBus.EBusback.domain.stop.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class StopPinResDto {
    private List<Integer> stopId;

    @Builder
    public StopPinResDto(List<Integer> stopId){
        this.stopId = stopId;
    }
}
