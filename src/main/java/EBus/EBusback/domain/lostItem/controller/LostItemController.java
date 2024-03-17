package EBus.EBusback.domain.lostItem.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import EBus.EBusback.domain.lostItem.dto.ItemPostReqDto;
import EBus.EBusback.domain.lostItem.dto.ItemPostResDto;
import EBus.EBusback.domain.lostItem.dto.ItemSearchResDto;
import EBus.EBusback.domain.lostItem.service.LostItemService;
import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.global.SecurityUtil;
import EBus.EBusback.global.exception.ErrorCode;
import EBus.EBusback.global.service.S3Uploader;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "분실물", description = "분실물 글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class LostItemController {
	private final LostItemService lostItemService;
	private final S3Uploader s3Uploader;

	@Operation(summary = "분실물 글 등록")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "등록 성공"),
			@ApiResponse(responseCode = "401", description = "로그인 필요")
	})
	// 분실물 글 등록
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ItemPostResDto createLostItemPost(@RequestPart(value = "image") MultipartFile image,
		@RequestPart(value = "dto") @Valid ItemPostReqDto itemPostReqDto) throws IOException {
		Member writer = SecurityUtil.getCurrentUser();
		if (writer == null)
			throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());
		String imageUrl = s3Uploader.upload(image, "image");
		return lostItemService.createLostItemPost(writer, imageUrl, itemPostReqDto);
	}

	@Operation(summary = "분실물 글 검색")
	@ApiResponse(responseCode = "200", description = "조회 성공")
	// 분실물 글 검색
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ItemSearchResDto> getSearchList(@RequestParam(required = false) String keyword,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return lostItemService.getSearchList(keyword, date);
	}

	@Operation(summary = "분실물 글 삭제")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "삭제 성공"),
			@ApiResponse(responseCode = "401", description = "로그인 필요"),
			@ApiResponse(responseCode = "403", description = "작성자 아님"),
			@ApiResponse(responseCode = "404", description = "해당 id를 갖는 글 없음")
	})
	// 분실물 글 삭제
	@DeleteMapping("/{item_id}")
	@ResponseStatus(value = HttpStatus.OK)
	public String deleteLostItem(@PathVariable("item_id") Long itemId) {
		Member writer = SecurityUtil.getCurrentUser();
		if (writer == null)
			throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());
		lostItemService.deleteLostItem(writer, itemId);
		return "글이 삭제되었습니다.";
	}

	@Operation(summary = "핀한 정류장 리스트 조회")
	@ApiResponse(responseCode = "200", description = "조회 성공")
	// 분실물 글 상세 조회
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ItemPostResDto getLostItemDetail (@PathVariable("item_id") Long itemId){
		return lostItemService.getLostItemDetail(itemId);
	}
}
