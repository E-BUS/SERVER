package EBus.EBusback.domain.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import EBus.EBusback.domain.heart.service.HeartService;
import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.domain.post.dto.PostCreateResponseDto;
import EBus.EBusback.domain.post.dto.PostDetailResponseDto;
import EBus.EBusback.domain.post.dto.PostMemberDto;
import EBus.EBusback.domain.post.dto.PostRequestDto;
import EBus.EBusback.domain.post.entity.Post;
import EBus.EBusback.domain.post.repository.PostRepository;
import EBus.EBusback.global.SecurityUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final HeartService heartService;

	public PostCreateResponseDto createPost(PostRequestDto requestDto, Boolean isSuggestion) {
		Member writer = SecurityUtil.getCurrentUser();
		Post post = postRepository.save(
			Post.builder()
				.title(requestDto.getTitle())
				.content(requestDto.getContent())
				.isSuggestion(isSuggestion)
				.writer(writer)
				.build()
		);
		return new PostCreateResponseDto(post);
	}

	public PostDetailResponseDto findPost(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다."));
		Member member = SecurityUtil.getCurrentUser();
		return new PostDetailResponseDto(new PostCreateResponseDto(post), heartService.findHeartCount(post),
			findPostMemberInfo(post, member));
	}

	public PostMemberDto findPostMemberInfo(Post post, Member member) {
		Boolean hasHeart = false;
		Boolean isWriter = false;

		if (member != null) {
			// hasHeart

			// isWriter
			isWriter = post.getWriter().equals(member);
		}
		return new PostMemberDto(hasHeart, isWriter);
	}
}
