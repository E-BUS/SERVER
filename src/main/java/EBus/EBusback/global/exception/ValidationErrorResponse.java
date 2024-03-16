package EBus.EBusback.global.exception;

import java.util.Date;

import lombok.Getter;

// validation 예외 처리를 위한 response
@Getter
public class ValidationErrorResponse {

	private Date timestamp;
	private Integer status;
	private String error;
	private String message;

	public ValidationErrorResponse(Date timestamp, Integer status, String error, String message) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
	}
}
