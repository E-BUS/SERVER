package EBus.EBusback.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import EBus.EBusback.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	@PatchMapping("/role")
	@ResponseStatus(HttpStatus.OK)
	public String changeToAdmin() {
		memberService.changeToAdmin();
		return "관리자로 변경되었습니다.";
	}
}
