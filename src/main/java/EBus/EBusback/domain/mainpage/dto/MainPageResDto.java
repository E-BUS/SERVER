package EBus.EBusback.domain.mainpage.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class MainPageResDto {
    private List<MainNoticeResDto> notice;
    private List<MainItemResDto> items;
    private List<MainPostResDto> suggestion;
    private List<MainPostResDto> appreciation;

    @Builder
    public MainPageResDto(List<MainNoticeResDto> notice, List<MainItemResDto> items, List<MainPostResDto> suggestion, List<MainPostResDto> appreciation){
        this.notice = notice;
        this.items = items;
        this.suggestion = suggestion;
        this.appreciation = appreciation;
    }
}
