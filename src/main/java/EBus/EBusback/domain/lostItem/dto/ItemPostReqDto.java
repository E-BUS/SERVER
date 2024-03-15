package EBus.EBusback.domain.lostItem.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemPostReqDto {
    private String title;
    private Date foundDate;
    private String foundTime;
    private String foundLocation;
    private String depository;
}
