package EBus.EBusback.domain.post.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import EBus.EBusback.domain.post.dto.PostCreateResponseDto;
import EBus.EBusback.domain.post.dto.PostDetailResponseDto;
import EBus.EBusback.domain.post.dto.PostOutlineResponseDto;
import EBus.EBusback.domain.post.dto.PostRequestDto;
import EBus.EBusback.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	@PostMapping("/suggestion")
	@ResponseStatus(HttpStatus.CREATED)
	public PostCreateResponseDto createSuggestion(@RequestBody PostRequestDto requestDto) {
		return postService.createPost(requestDto, true);
	}

	@PostMapping("/appreciation")
	@ResponseStatus(HttpStatus.CREATED)
	public PostCreateResponseDto createAppreciation(@RequestBody PostRequestDto requestDto) {
		return postService.createPost(requestDto, false);
	}

	@GetMapping("/{postId}")
	@ResponseStatus(HttpStatus.OK)
	public PostDetailResponseDto findPost(@PathVariable Long postId) {
		return postService.findPost(postId);
	}

	@GetMapping("/suggestion")
	@ResponseStatus(HttpStatus.OK)
	public List<PostOutlineResponseDto> findSuggestionList() {
		return postService.findPostList(true);
	}

	@GetMapping("/appreciation")
	@ResponseStatus(HttpStatus.OK)
	public List<PostOutlineResponseDto> findAppreciationList() {
		return postService.findPostList(false);
	}
}
