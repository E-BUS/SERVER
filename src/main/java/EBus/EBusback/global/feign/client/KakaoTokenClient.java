package EBus.EBusback.global.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import EBus.EBusback.global.feign.dto.KakaoTokenRequestDto;
import EBus.EBusback.global.feign.dto.KakaoTokenResponseDto;

// 카카오로부터 access token을 응답으로 받기 위해 http 요청을 보내는 open feign 클라이언트
@FeignClient(name = "kakaoToken", url = "https://kauth.kakao.com/oauth/token")
public interface KakaoTokenClient {

	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	KakaoTokenResponseDto getAccessToken(@RequestBody KakaoTokenRequestDto requestDto);
}
