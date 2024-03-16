package EBus.EBusback.domain.stop.entity;

import EBus.EBusback.domain.member.entity.Member;
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
public class PinStop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pinId;

	@Column(nullable = false)
	private Boolean isValid;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "stop_id")
	private BusStop stop;

	@Builder
	public PinStop(Member member, BusStop stop) {
		this.member = member;
		this.stop = stop;
		this.isValid = true;
	}

	public void updatePinStop(Boolean isValid) {
		this.isValid = isValid;
	}
}
