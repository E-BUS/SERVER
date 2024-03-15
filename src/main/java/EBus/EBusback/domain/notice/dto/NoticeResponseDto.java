package EBus.EBusback.domain.notice.dto;

import java.time.LocalDateTime;

import EBus.EBusback.domain.notice.entity.Notice;
import lombok.Getter;

@Getter
public class NoticeResponseDto {

	private Long noticeId;
	private String title;
	private String content;
	private LocalDateTime createdDate;

	public NoticeResponseDto(Notice notice) {
		this.noticeId = notice.getNoticeId();
		this.title = notice.getTitle();
		this.content = notice.getContent();
		this.createdDate = notice.getCreatedDate();
	}
}
