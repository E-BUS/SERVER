package EBus.EBusback.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import EBus.EBusback.domain.member.dto.LoginRequestDto;
import EBus.EBusback.domain.member.dto.LoginResponseDto;
import EBus.EBusback.domain.member.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "인증", description = "인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	@Operation(summary = "로그인")
	@ApiResponse(responseCode = "201", description = "로그인 성공")
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.CREATED)
	public LoginResponseDto login(@RequestBody LoginRequestDto requestDto) {
		return authService.login(requestDto.getCode());
	}
}
