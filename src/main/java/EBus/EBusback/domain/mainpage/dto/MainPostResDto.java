package EBus.EBusback.domain.mainpage.dto;

import EBus.EBusback.domain.post.entity.Post;
import lombok.Getter;

@Getter
public class MainPostResDto {
    private Long postId;
    private String title;

    public MainPostResDto(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
    }
}
