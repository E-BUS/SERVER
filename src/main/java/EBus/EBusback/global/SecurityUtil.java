package EBus.EBusback.global;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import EBus.EBusback.domain.member.entity.Member;

public class SecurityUtil {

	// 현재 사용자 정보 가져오기
	// 로그인 안 했을 때 (Authorization 헤더 없을 때) anonymousUser
	public static Member getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getPrincipal() == null
			|| authentication.getName().equals("anonymousUser")) {
			return null;
		}
		return (Member)authentication.getPrincipal();
	}
}
