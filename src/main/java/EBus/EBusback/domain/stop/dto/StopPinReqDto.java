package EBus.EBusback.domain.stop.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class StopPinReqDto {
    private List<Boolean> stopPinned;
}
