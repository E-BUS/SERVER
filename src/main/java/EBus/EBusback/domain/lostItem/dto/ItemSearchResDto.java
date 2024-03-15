package EBus.EBusback.domain.lostItem.dto;

import EBus.EBusback.domain.lostItem.entity.LostItem;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ItemSearchResDto {
    private Long itemId;
    private String writerRole;
    private String title;
    private String image;
    private LocalDate foundDate;

    @Builder
    public ItemSearchResDto(LostItem lostItem){
        this.itemId = lostItem.getItemId();
        this.writerRole = lostItem.getWriter().getRole().toString();
        this.title = lostItem.getTitle();
        this.image = lostItem.getImage();
        this.foundDate = lostItem.getFoundDate();
    }
}
