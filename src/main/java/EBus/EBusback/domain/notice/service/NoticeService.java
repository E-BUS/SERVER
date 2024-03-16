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

	public NoticeResponseDto createNotice(PostRequestDto requestDto) {
		Member member = SecurityUtil.getCurrentUser();
		if (member == null)
			throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());
		if (!member.getRole().equals(Role.ADMIN))
			throw new ResponseStatusException(ErrorCode.NO_ADMIN.getStatus(), ErrorCode.NO_ADMIN.getMessage());
		return new NoticeResponseDto(noticeRepository.save(
			Notice.builder().title(requestDto.getTitle()).content(requestDto.getContent()).writer(member).build()
		));
	}

	public NoticeResponseDto findNotice(Long noticeId) {
		Notice notice = noticeRepository.findById(noticeId)
			.orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));
		return new NoticeResponseDto(notice);
	}

	public List<NoticeResponseDto> findNoticeList() {
		return noticeRepository.findAll().stream()
			.map(NoticeResponseDto::new).collect(Collectors.toList());
	}

	public void removeNotice(Long noticeId) {
		Member member = SecurityUtil.getCurrentUser();
		if (member == null)
			throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());
		Notice notice = noticeRepository.findById(noticeId)
			.orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));
		if (!member.getRole().equals(Role.ADMIN))
			throw new ResponseStatusException(ErrorCode.NO_ADMIN.getStatus(), ErrorCode.NO_ADMIN.getMessage());
		noticeRepository.delete(notice);
	}
}
