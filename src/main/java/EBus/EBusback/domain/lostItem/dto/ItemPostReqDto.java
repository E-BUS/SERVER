package EBus.EBusback.domain.lostItem.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemPostReqDto {
    private String title;
    private LocalDate foundDate;
    private String foundTime;
    private String foundLocation;
    private String depository;
}
