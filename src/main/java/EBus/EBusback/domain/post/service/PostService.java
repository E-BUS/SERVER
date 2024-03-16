package EBus.EBusback.domain.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import EBus.EBusback.domain.heart.service.HeartService;
import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.domain.post.dto.PostCreateResponseDto;
import EBus.EBusback.domain.post.dto.PostDetailResponseDto;
import EBus.EBusback.domain.post.dto.PostMemberDto;
import EBus.EBusback.domain.post.dto.PostOutlineResponseDto;
import EBus.EBusback.domain.post.dto.PostRequestDto;
import EBus.EBusback.domain.post.entity.Post;
import EBus.EBusback.domain.post.repository.PostRepository;
import EBus.EBusback.global.SecurityUtil;
import EBus.EBusback.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final HeartService heartService;

	public PostCreateResponseDto createPost(PostRequestDto requestDto, Boolean isSuggestion) {
		Member writer = SecurityUtil.getCurrentUser();
		if (writer == null)
			throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());
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
			.orElseThrow(() -> new ResponseStatusException(
				ErrorCode.NO_POST_EXIST.getStatus(), ErrorCode.NO_POST_EXIST.getMessage()));
		Member member = SecurityUtil.getCurrentUser();
		return new PostDetailResponseDto(new PostCreateResponseDto(post), heartService.getHeartCount(post),
			findPostMemberInfo(post, member));
	}

	public PostMemberDto findPostMemberInfo(Post post, Member member) {
		Boolean hasHeart = false;
		Boolean isWriter = false;

		if (member != null) {
			hasHeart = heartService.existsHeart(member, post);
			isWriter = post.getWriter().getMemberId().equals(member.getMemberId());
		}
		return new PostMemberDto(hasHeart, isWriter);
	}

	public List<PostOutlineResponseDto> findPostList(Boolean isSuggestion) {
		List<Post> postList = postRepository.findAllByIsSuggestion(isSuggestion);
		return postList.stream()
			.map(post -> new PostOutlineResponseDto(post, heartService.getHeartCount(post)))
			.collect(Collectors.toList());
	}

	public void removePost(Long postId) {
		Member member = SecurityUtil.getCurrentUser();
		if (member == null)
			throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ResponseStatusException(
				ErrorCode.NO_POST_EXIST.getStatus(), ErrorCode.NO_POST_EXIST.getMessage()));

		if (!post.getWriter().getMemberId().equals(member.getMemberId()))
			throw new ResponseStatusException(ErrorCode.NO_WRITER.getStatus(), ErrorCode.NO_WRITER.getMessage());
		postRepository.delete(post);
	}
}
