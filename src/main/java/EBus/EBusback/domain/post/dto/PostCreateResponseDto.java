package EBus.EBusback.domain.post.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateResponseDto {

	private Long postId;
	private String title;
	private String content;
	private Boolean isSuggestion;
	private LocalDateTime createdDate;
}
