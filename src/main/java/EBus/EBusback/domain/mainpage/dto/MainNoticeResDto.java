package EBus.EBusback.domain.mainpage.dto;

import EBus.EBusback.domain.notice.entity.Notice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MainNoticeResDto {
    private Long noticeId;
    private String title;
    private LocalDateTime createdDate;

    public MainNoticeResDto(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.title = notice.getTitle();
        this.createdDate = notice.getCreatedDate();
    }
}