package EBus.EBusback.domain.stop.controller;

import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.domain.stop.dto.StopPinReqDto;
import EBus.EBusback.domain.stop.service.StopService;
import EBus.EBusback.global.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stops")
public class StopController {
    private final StopService stopService;

    // 정류장 핀 등록/취소
    @PostMapping("/pin")
    public List<Integer> createOrRemovePin(@RequestBody StopPinReqDto stopPinReqDto){
        Member member = SecurityUtil.getCurrentUser();
        return stopService.createOrRemovePin(member, stopPinReqDto);
    }
}
