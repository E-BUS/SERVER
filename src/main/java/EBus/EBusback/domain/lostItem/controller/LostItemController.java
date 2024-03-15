package EBus.EBusback.domain.lostItem.controller;

import EBus.EBusback.domain.lostItem.dto.ItemPostReqDto;
import EBus.EBusback.domain.lostItem.dto.ItemPostResDto;
import EBus.EBusback.domain.lostItem.service.LostItemService;
import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.global.SecurityUtil;
import EBus.EBusback.global.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class LostItemController {
    private final LostItemService lostItemService;
    private final S3Uploader s3Uploader;

    // 분실물 글 등록
    @PostMapping(value = "/items", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ItemPostResDto createLostItemPost(@RequestPart(value = "image") MultipartFile image, @RequestPart(value = "dto") ItemPostReqDto itemPostReqDto) throws IOException {
        Member writer = SecurityUtil.getCurrentUser();
        String imageUrl = s3Uploader.upload(image, "image");
        return lostItemService.createLostItemPost(writer, imageUrl, itemPostReqDto);
    }
}
