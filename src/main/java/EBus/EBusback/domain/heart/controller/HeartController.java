package EBus.EBusback.domain.heart.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import EBus.EBusback.domain.heart.dto.HeartRequestDto;
import EBus.EBusback.domain.heart.service.HeartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "건의해요/고마워요", description = "건의해요/고마워요 글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/hearts")
public class HeartController {

	private final HeartService heartService;

	@Operation(summary = "좋아요 등록/취소")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "좋아요 등록"),
		@ApiResponse(responseCode = "201", description = "좋아요 취소"),
		@ApiResponse(responseCode = "401", description = "로그인 필요"),
		@ApiResponse(responseCode = "404", description = "해당 id를 갖는 글 없음")
	})
	@PostMapping
	public String clickHeart(@RequestBody @Valid HeartRequestDto requestDto) {
		return heartService.createOrRemoveHeart(requestDto.getPostId());
	}
}
