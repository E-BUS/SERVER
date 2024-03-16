package EBus.EBusback.domain.mainpage.controller;

import EBus.EBusback.domain.mainpage.dto.MainPageResDto;
import EBus.EBusback.domain.mainpage.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainPageController {
    private final MainPageService mainPageService;

    // 메인 페이지 글 목록 조회
    @GetMapping("/main")
    @ResponseStatus(HttpStatus.OK)
    public MainPageResDto getMainPage(){
        return mainPageService.getMainPage();
    }
}
