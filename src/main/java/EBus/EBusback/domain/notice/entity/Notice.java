package EBus.EBusback.domain.notice.entity;

import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Notice extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long noticeId;

	@Column(nullable = false, length = 30)
	private String title;

	@Column(nullable = false, length = 100)
	private String content;

	@ManyToOne
	@JoinColumn(name = "writer_id")
	private Member writer;

	@Builder
	public Notice(String title, String content, Member writer) {
		this.title = title;
		this.content = content;
		this.writer = writer;
	}
}
