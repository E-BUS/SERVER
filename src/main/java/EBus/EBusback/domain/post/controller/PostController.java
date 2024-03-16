package EBus.EBusback.domain.post.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "건의해요/고마워요", description = "건의해요/고마워요 글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	@Operation(summary = "건의해요 글 등록")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "등록 성공"),
		@ApiResponse(responseCode = "401", description = "로그인 필요")
	})
	@PostMapping("/suggestion")
	@ResponseStatus(HttpStatus.CREATED)
	public PostCreateResponseDto createSuggestion(@RequestBody @Valid PostRequestDto requestDto) {
		return postService.createPost(requestDto, true);
	}

	@Operation(summary = "고마워요 글 등록")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "등록 성공"),
		@ApiResponse(responseCode = "401", description = "로그인 필요")
	})
	@PostMapping("/appreciation")
	@ResponseStatus(HttpStatus.CREATED)
	public PostCreateResponseDto createAppreciation(@RequestBody @Valid PostRequestDto requestDto) {
		return postService.createPost(requestDto, false);
	}

	@Operation(summary = "건의해요/고마워요 글 상세 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공"),
		@ApiResponse(responseCode = "404", description = "해당 id를 갖는 글 없음")
	})
	@GetMapping("/{postId}")
	@ResponseStatus(HttpStatus.OK)
	public PostDetailResponseDto findPost(@PathVariable Long postId) {
		return postService.findPost(postId);
	}

	@Operation(summary = "건의해요 글 리스트 조회")
	@ApiResponse(responseCode = "200", description = "조회 성공")
	@GetMapping("/suggestion")
	@ResponseStatus(HttpStatus.OK)
	public List<PostOutlineResponseDto> findSuggestionList() {
		return postService.findPostList(true);
	}

	@Operation(summary = "고마워요 글 리스트 조회")
	@ApiResponse(responseCode = "200", description = "조회 성공")
	@GetMapping("/appreciation")
	@ResponseStatus(HttpStatus.OK)
	public List<PostOutlineResponseDto> findAppreciationList() {
		return postService.findPostList(false);
	}

	@Operation(summary = "건의해요/고마워요 글 삭제")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공"),
		@ApiResponse(responseCode = "401", description = "로그인 필요"),
		@ApiResponse(responseCode = "403", description = "작성자 아님"),
		@ApiResponse(responseCode = "404", description = "해당 id를 갖는 글 없음")
	})
	@DeleteMapping("/{postId}")
	@ResponseStatus(HttpStatus.OK)
	public String removePost(@PathVariable Long postId) {
		postService.removePost(postId);
		return "글이 삭제되었습니다.";
	}

}
