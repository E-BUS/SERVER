package EBus.EBusback.domain.mainpage.controller;

import EBus.EBusback.domain.mainpage.dto.MainPageResDto;
import EBus.EBusback.domain.mainpage.service.MainPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "메인 페이지", description = "메인 페이지 관련 API")
@RestController
@RequiredArgsConstructor
public class MainPageController {
    private final MainPageService mainPageService;

    @Operation(summary = "메인 페이지 글 목록 조회")
    @ApiResponse(responseCode = "200", description = "글 목록 조회")
    // 메인 페이지 글 목록 조회
    @GetMapping("/main")
    @ResponseStatus(HttpStatus.OK)
    public MainPageResDto getMainPage(){
        return mainPageService.getMainPage();
    }
}
