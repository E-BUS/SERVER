package EBus.EBusback.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	// File
	CONVERT_MULTIPARTFILE_ERROR(HttpStatus.BAD_REQUEST, "MultipartFile을 File로 전환하는 것에 실패했습니다."),

	// Post
	NO_POST_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다.");

	private final HttpStatus status;
	private final String message;
}
