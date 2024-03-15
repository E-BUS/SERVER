package EBus.EBusback.domain.table.entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class TimeTableNight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer nightId;

	@Column(nullable = false)
	private LocalTime departureTime;

	@Column(nullable = false)
	private Boolean isUpbound;

	@Column(nullable = false)
	private Boolean isWeekday;
}
