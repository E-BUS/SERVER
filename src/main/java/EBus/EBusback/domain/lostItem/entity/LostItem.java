package EBus.EBusback.domain.lostItem.entity;

import EBus.EBusback.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LostItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Date foundDate;

    @Column(nullable = false, length = 30)
    private String foundTime;

    @Column(nullable = false, length = 50)
    private String foundLocation;

    @Column(nullable = false, length = 50)
    private String depository;
}
