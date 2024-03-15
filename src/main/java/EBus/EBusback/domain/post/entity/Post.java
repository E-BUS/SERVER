package EBus.EBusback.domain.post.entity;

import EBus.EBusback.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 100)
    private String content;

    @Column(nullable = false)
    private Boolean isSuggestion;

    @Builder
    public Post(Member writer, String title, String content, Boolean isSuggestion){
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.isSuggestion = isSuggestion;
    }
}
