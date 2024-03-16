package EBus.EBusback.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import EBus.EBusback.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "사용자", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	@Operation(summary = "관리자로 전환")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "관리자로 전환 성공"),
		@ApiResponse(responseCode = "401", description = "로그인 필요")
	})
	@PatchMapping("/role")
	@ResponseStatus(HttpStatus.OK)
	public String changeToAdmin() {
		memberService.changeToAdmin();
		return "관리자로 변경되었습니다.";
	}
}
