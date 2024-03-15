package EBus.EBusback.domain.notice.service;

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
}
