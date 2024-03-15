package EBus.EBusback.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDetailResponseDto {

	private PostCreateResponseDto post;
	private Long heartCount;
	private PostMemberDto member;
}
