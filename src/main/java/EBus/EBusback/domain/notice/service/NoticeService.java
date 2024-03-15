package EBus.EBusback.domain.notice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.domain.member.entity.Role;
import EBus.EBusback.domain.notice.dto.NoticeResponseDto;
import EBus.EBusback.domain.notice.entity.Notice;
import EBus.EBusback.domain.notice.repository.NoticeRepository;
import EBus.EBusback.domain.post.dto.PostRequestDto;
import EBus.EBusback.global.SecurityUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;

	public NoticeResponseDto createNotice(PostRequestDto requestDto) {
		Member member = SecurityUtil.getCurrentUser();
		if (!member.getRole().equals(Role.ADMIN))
			throw new RuntimeException("공지사항은 관리자만 작성 가능합니다.");
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
		Notice notice = noticeRepository.findById(noticeId)
			.orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));
		if (!member.getRole().equals(Role.ADMIN))
			throw new RuntimeException("관리자만 삭제할 수 있습니다.");
		noticeRepository.delete(notice);
	}
}
