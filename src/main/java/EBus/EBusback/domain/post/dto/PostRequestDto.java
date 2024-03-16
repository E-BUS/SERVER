package EBus.EBusback.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostRequestDto {

	@NotEmpty
	@NotBlank
	@Size(min = 1, max = 30)
	private String title;

	@NotEmpty
	@NotBlank
	@Size(min = 1, max = 100)
	private String content;
}
