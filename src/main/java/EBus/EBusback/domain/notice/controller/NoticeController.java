package EBus.EBusback.domain.notice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import EBus.EBusback.domain.notice.dto.NoticeResponseDto;
import EBus.EBusback.domain.notice.service.NoticeService;
import EBus.EBusback.domain.post.dto.PostRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {

	private final NoticeService noticeService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public NoticeResponseDto createNotice(@RequestBody @Valid PostRequestDto requestDto) {
		return noticeService.createNotice(requestDto);
	}

	@GetMapping("/{noticeId}")
	@ResponseStatus(HttpStatus.OK)
	public NoticeResponseDto findNotice(@PathVariable Long noticeId) {
		return noticeService.findNotice(noticeId);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<NoticeResponseDto> findNoticeList() {
		return noticeService.findNoticeList();
	}

	@DeleteMapping("/{noticeId}")
	@ResponseStatus(HttpStatus.OK)
	public String removeNotice(@PathVariable Long noticeId) {
		noticeService.removeNotice(noticeId);
		return "공지사항이 삭제되었습니다.";
	}
}
