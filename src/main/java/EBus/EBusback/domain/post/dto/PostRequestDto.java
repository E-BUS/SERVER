package EBus.EBusback.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostRequestDto {

	@NotBlank
	@Size(min = 1, max = 30)
	private String title;

	@NotBlank
	@Size(min = 1, max = 100)
	private String content;
}
