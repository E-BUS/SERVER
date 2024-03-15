package EBus.EBusback.domain.mainpage.dto;

import EBus.EBusback.domain.lostItem.entity.LostItem;
import lombok.Getter;

@Getter
public class MainItemResDto {
    private Long itemId;
    private String image;

    public MainItemResDto(LostItem lostItem){
        this.itemId = lostItem.getItemId();
        this.image = lostItem.getImage();
    }
}
