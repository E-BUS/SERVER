package EBus.EBusback.domain.stop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class BusStop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer stopId;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Stop name;
}
