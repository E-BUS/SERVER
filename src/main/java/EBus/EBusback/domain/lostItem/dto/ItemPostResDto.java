package EBus.EBusback.domain.lostItem.dto;

import EBus.EBusback.domain.lostItem.entity.LostItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ItemPostResDto {
    private Long itemId;
    private String writerRole;
    private String title;
    private String image;
    private LocalDate foundDate;
    private String foundTime;
    private String foundLocation;
    private String depository;

    @Builder
    public ItemPostResDto(LostItem lostItem){
        this.itemId = lostItem.getItemId();
        this.writerRole = lostItem.getWriter().getRole().toString();
        this.title = lostItem.getTitle();
        this.image = lostItem.getImage();
        this.foundDate = lostItem.getFoundDate();
        this.foundTime = lostItem.getFoundTime();
        this.foundLocation = lostItem.getFoundLocation();
        this.depository = lostItem.getDepository();
    }
}
