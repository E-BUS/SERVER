package EBus.EBusback.global.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import EBus.EBusback.global.feign.dto.KakaoUserResponseDto;

// 카카오로부터 사용자 정보를 응답으로 받기 위해 http 요청을 보내는 open feign 클라이언트
@FeignClient(name = "kakaoUser", url = "https://kapi.kakao.com/v2/user/me")
public interface KakaoUserClient {

	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	KakaoUserResponseDto getUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization);
}
