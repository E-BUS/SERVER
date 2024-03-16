package EBus.EBusback.domain.heart.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import EBus.EBusback.domain.heart.dto.HeartRequestDto;
import EBus.EBusback.domain.heart.service.HeartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hearts")
public class HeartController {

	private final HeartService heartService;

	@PostMapping
	public String clickHeart(@RequestBody @Valid HeartRequestDto requestDto) {
		return heartService.createOrRemoveHeart(requestDto.getPostId());
	}
}
