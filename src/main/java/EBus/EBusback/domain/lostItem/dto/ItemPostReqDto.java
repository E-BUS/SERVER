package EBus.EBusback.domain.lostItem.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemPostReqDto {

	@NotEmpty
	@NotBlank
	@Size(min = 1, max = 30)
	private String title;

	@NotNull
	private LocalDate foundDate;

	@NotNull
	@Size(max = 30)
	private String foundTime;

	@NotNull
	@Size(max = 50)
	private String foundLocation;

	@NotNull
	@Size(max = 50)
	private String depository;
}
