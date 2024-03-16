package EBus.EBusback.domain.stop.controller;

import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.domain.stop.dto.PinnedStopTimeResDto;
import EBus.EBusback.domain.stop.dto.StopPinReqDto;
import EBus.EBusback.domain.stop.dto.StopPinResDto;
import EBus.EBusback.domain.stop.dto.WholeTimetableResDto;
import EBus.EBusback.domain.stop.service.StopService;
import EBus.EBusback.global.SecurityUtil;
import EBus.EBusback.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stops")
public class StopController {
    private final StopService stopService;

    // 정류장 핀 등록/취소
    @PostMapping("/pin")
    public StopPinResDto createOrRemovePin(@RequestBody StopPinReqDto stopPinReqDto){
        Member member = SecurityUtil.getCurrentUser();
        if (member == null)
            throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());
        return stopService.createOrRemovePin(member, stopPinReqDto);
    }

    // 핀한 정류장 리스트 조회
    @GetMapping("/pin")
    public StopPinResDto getPinnedStopList(){
        Member member = SecurityUtil.getCurrentUser();
        if (member == null)
            throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());
        return stopService.getPinnedStopList(member);
    }

    // 특정 정류장 전체 시간표 조회
    @GetMapping("/{stop_id}/all")
    @ResponseStatus(HttpStatus.OK)
    public WholeTimetableResDto getWholeTimetable(@PathVariable("stop_id") Integer stopId){
        return stopService.getWholeTimetable(stopId);
    }

    // 핀한 정류장 일부 시간표 조회
    @GetMapping("/{stop_id}")
    @ResponseStatus(HttpStatus.OK)
    public PinnedStopTimeResDto getPartTimetable(@PathVariable("stop_id") Integer stopId){
        return stopService.getPartTimetable(stopId);
    }
}
