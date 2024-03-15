package EBus.EBusback.domain.post.dto;

import java.time.LocalDateTime;

import EBus.EBusback.domain.post.entity.Post;
import lombok.Getter;

@Getter
public class PostCreateResponseDto {

	private Long postId;
	private String title;
	private String content;
	private Boolean isSuggestion;
	private LocalDateTime createdDate;

	public PostCreateResponseDto(Post post) {
		this.postId = post.getPostId();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.isSuggestion = post.getIsSuggestion();
		this.createdDate = post.getCreatedDate();
	}
}
