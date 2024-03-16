package EBus.EBusback.domain.stop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class TimeResponseDto {
    private String route;
    private LocalTime time;

    @Builder
    public TimeResponseDto(String route, LocalTime time){
        this.route = route;
        this.time = time;
    }
}
