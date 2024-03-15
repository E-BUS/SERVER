package EBus.EBusback.global.feign.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoTokenRequestDto {

	private String grant_type;
	private String client_id;
	private String redirect_uri;
	private String code;
	private String client_secret;
}
