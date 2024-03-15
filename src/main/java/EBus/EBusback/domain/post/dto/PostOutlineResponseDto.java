package EBus.EBusback.domain.post.dto;

import java.time.LocalDateTime;

import EBus.EBusback.domain.post.entity.Post;
import lombok.Getter;

@Getter
public class PostOutlineResponseDto {

	private Long postId;
	private String title;
	private LocalDateTime createdDate;
	private Integer heartCount;

	public PostOutlineResponseDto(Post post, Integer heartCount) {
		this.postId = post.getPostId();
		this.title = post.getTitle();
		this.createdDate = post.getCreatedDate();
		this.heartCount = heartCount;
	}
}
