package EBus.EBusback.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Member {

	@Id
	private Long memberId;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Role role;

	@Builder
	public Member(Long memberId) {
		this.memberId = memberId;
		this.role = Role.USER;
	}
}
