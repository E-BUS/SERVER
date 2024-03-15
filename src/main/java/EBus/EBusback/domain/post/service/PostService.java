package EBus.EBusback.domain.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.domain.post.dto.PostRequestDto;
import EBus.EBusback.domain.post.dto.PostResponseDto;
import EBus.EBusback.domain.post.entity.Post;
import EBus.EBusback.domain.post.repository.PostRepository;
import EBus.EBusback.global.SecurityUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public PostResponseDto createPost(PostRequestDto requestDto, Boolean isSuggestion) {
		Member writer = SecurityUtil.getCurrentUser();
		Post post = postRepository.save(
			Post.builder()
				.title(requestDto.getTitle())
				.content(requestDto.getContent())
				.isSuggestion(isSuggestion)
				.writer(writer)
				.build()
		);
		return new PostResponseDto(post.getPostId(), post.getTitle(), post.getContent(), post.getIsSuggestion(),
			post.getCreatedDate());
	}
}
