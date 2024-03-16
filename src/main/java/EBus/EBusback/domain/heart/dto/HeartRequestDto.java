package EBus.EBusback.domain.heart.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class HeartRequestDto {

	@NotNull
	private Long postId;
}
