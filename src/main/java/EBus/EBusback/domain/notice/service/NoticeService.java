package EBus.EBusback.domain.notice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.domain.member.entity.Role;
import EBus.EBusback.domain.notice.dto.NoticeResponseDto;
import EBus.EBusback.domain.notice.entity.Notice;
import EBus.EBusback.domain.notice.repository.NoticeRepository;
import EBus.EBusback.domain.post.dto.PostRequestDto;
import EBus.EBusback.global.SecurityUtil;
import EBus.EBusback.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;

	// 공지사항 등록
	public NoticeResponseDto createNotice(PostRequestDto requestDto) {
		Member member = SecurityUtil.getCurrentUser();
		if (member == null)
			throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());
		if (!member.getRole().equals(Role.ADMIN))  // 사용자가 관리자인지 확인
			throw new ResponseStatusException(ErrorCode.NO_ADMIN.getStatus(), ErrorCode.NO_ADMIN.getMessage());
		return new NoticeResponseDto(noticeRepository.save(
			Notice.builder().title(requestDto.getTitle()).content(requestDto.getContent()).writer(member).build()
		));
	}

	// noticeId를 id로 가지는 공지사항 상세 조회
	public NoticeResponseDto findNotice(Long noticeId) {
		Notice notice = noticeRepository.findById(noticeId)
			.orElseThrow(() -> new ResponseStatusException(
				ErrorCode.NO_NOTICE_EXIST.getStatus(), ErrorCode.NO_NOTICE_EXIST.getMessage()));
		return new NoticeResponseDto(notice);
	}

	// 공지사항 전체 조회
	public List<NoticeResponseDto> findNoticeList() {
		List<Notice> notices = noticeRepository.findAllByOrderByCreatedDateDesc();
		return notices.stream().map(NoticeResponseDto::new).collect(Collectors.toList());
	}

	// noticeId를 id로 가지는 공지사항 삭제
	public void removeNotice(Long noticeId) {
		Member member = SecurityUtil.getCurrentUser();
		if (member == null)
			throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());
		Notice notice = noticeRepository.findById(noticeId)
			.orElseThrow(() -> new ResponseStatusException(
				ErrorCode.NO_NOTICE_EXIST.getStatus(), ErrorCode.NO_NOTICE_EXIST.getMessage()));
		if (!member.getRole().equals(Role.ADMIN))  // 사용자가 관리자인지 확인
			throw new ResponseStatusException(ErrorCode.NO_ADMIN.getStatus(), ErrorCode.NO_ADMIN.getMessage());
		noticeRepository.delete(notice);
	}
}
