package EBus.EBusback.domain.heart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import EBus.EBusback.domain.heart.entity.Heart;
import EBus.EBusback.domain.heart.repository.HeartRepository;
import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.domain.post.entity.Post;
import EBus.EBusback.domain.post.repository.PostRepository;
import EBus.EBusback.global.SecurityUtil;
import EBus.EBusback.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {

	private final HeartRepository heartRepository;
	private final PostRepository postRepository;

	// postId를 id로 가지는 게시글에 좋아요를 등록하거나 취소
	public String createOrRemoveHeart(Long postId) {
		Member member = SecurityUtil.getCurrentUser();
		if (member == null)
			throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ResponseStatusException(
				ErrorCode.NO_POST_EXIST.getStatus(), ErrorCode.NO_POST_EXIST.getMessage()));

		// 사용자가 이 글에 누른 좋아요가 있는지 조회
		// 있으면 해당 엔티티 가져오고
		// 없으면 좋아요 생성
		Heart heart = heartRepository.findByMemberAndPost(member, post)
			.orElseGet(() -> heartRepository.save(Heart.builder().member(member).post(post).build()));

		// 좋아요 여부를 반대로 변경: true -> false / false -> true
		// 좋아요를 새로 생성한 경우: 초기 좋아요 여부가 true이면 updateHeart() 메서드 호출 후 좋아요 여부가 false가 되므로
		// 						초기 좋아요 여부를 false로 설정
		heart.updateHeart();

		if (heart.getIsValid())
			return "좋아요가 등록되었습니다.";
		else
			return "좋아요가 취소되었습니다.";
	}

	public Boolean existsHeart(Member member, Post post) {
		return heartRepository.existsByMemberAndPostAndIsValid(member, post, true);
	}
}
