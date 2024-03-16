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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "공지사항", description = "공지사항 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {

	private final NoticeService noticeService;

	@Operation(summary = "공지사항 등록")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "등록 성공"),
		@ApiResponse(responseCode = "401", description = "로그인 필요"),
		@ApiResponse(responseCode = "403", description = "관리자 아님")
	})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public NoticeResponseDto createNotice(@RequestBody @Valid PostRequestDto requestDto) {
		return noticeService.createNotice(requestDto);
	}

	@Operation(summary = "공지사항 상세 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공"),
		@ApiResponse(responseCode = "404", description = "해당 id를 갖는 글 없음")
	})
	@GetMapping("/{noticeId}")
	@ResponseStatus(HttpStatus.OK)
	public NoticeResponseDto findNotice(@PathVariable Long noticeId) {
		return noticeService.findNotice(noticeId);
	}

	@Operation(summary = "공지사항 리스트 조회")
	@ApiResponse(responseCode = "200", description = "조회 성공")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<NoticeResponseDto> findNoticeList() {
		return noticeService.findNoticeList();
	}

	@Operation(summary = "공지사항 삭제")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공"),
		@ApiResponse(responseCode = "401", description = "로그인 필요"),
		@ApiResponse(responseCode = "403", description = "관리자 아님"),
		@ApiResponse(responseCode = "404", description = "해당 id를 갖는 글 없음")
	})
	@DeleteMapping("/{noticeId}")
	@ResponseStatus(HttpStatus.OK)
	public String removeNotice(@PathVariable Long noticeId) {
		noticeService.removeNotice(noticeId);
		return "공지사항이 삭제되었습니다.";
	}
}
