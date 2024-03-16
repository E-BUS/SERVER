package EBus.EBusback.global.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// validation 예외 처리를 위한 handler
@RestControllerAdvice
public class ValidationExceptionHandler {

	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<ValidationErrorResponse> validException(MethodArgumentNotValidException e) {

		ValidationErrorResponse response = new ValidationErrorResponse(
			new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
			e.getBindingResult().getAllErrors().get(0).getDefaultMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}