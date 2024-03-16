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

	public String createOrRemoveHeart(Long postId) {
		Member member = SecurityUtil.getCurrentUser();
		if (member == null)
			throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ResponseStatusException(
				ErrorCode.NO_POST_EXIST.getStatus(), ErrorCode.NO_POST_EXIST.getMessage()));

		Heart heart = heartRepository.findByMemberAndPost(member, post)
			.orElseGet(() -> heartRepository.save(Heart.builder().member(member).post(post).build()));

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