package EBus.EBusback.domain.stop.controller;

import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.domain.stop.dto.PinnedStopTimeResDto;
import EBus.EBusback.domain.stop.dto.StopPinReqDto;
import EBus.EBusback.domain.stop.dto.StopPinResDto;
import EBus.EBusback.domain.stop.dto.WholeTimetableResDto;
import EBus.EBusback.domain.stop.service.StopService;
import EBus.EBusback.global.SecurityUtil;
import EBus.EBusback.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "정류장", description = "정류장 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/stops")
public class StopController {
    private final StopService stopService;

    @Operation(summary = "정류장 핀 등록 및 취소")
    @ApiResponse(responseCode = "200", description = "핀 여부 반영 성공")
    @ApiResponse(responseCode = "401", description = "로그인 필요")
    // 정류장 핀 등록/취소
    @PostMapping("/pin")
    public StopPinResDto createOrRemovePin(@RequestBody @Valid StopPinReqDto stopPinReqDto){
        Member member = SecurityUtil.getCurrentUser();
        if (member == null)
            throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());
        return stopService.createOrRemovePin(member, stopPinReqDto);
    }

    @Operation(summary = "핀한 정류장 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 필요")
    })
    // 핀한 정류장 리스트 조회
    @GetMapping("/pin")
    public StopPinResDto getPinnedStopList(){
        Member member = SecurityUtil.getCurrentUser();
        if (member == null)
            throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());
        return stopService.getPinnedStopList(member);
    }

    @Operation(summary = "특정 정류장의 전체 시간표 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    // 특정 정류장 전체 시간표 조회
    @GetMapping("/{stop_id}/all")
    @ResponseStatus(HttpStatus.OK)
    public WholeTimetableResDto getWholeTimetable(@PathVariable("stop_id") Integer stopId){
        return stopService.getWholeTimetable(stopId);
    }

    @Operation(summary = "핀한 정류장 일부 시간표 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 필요")
    })
    // 핀한 정류장 일부 시간표 조회
    @GetMapping("/{stop_id}")
    @ResponseStatus(HttpStatus.OK)
    public PinnedStopTimeResDto getPartTimetable(@PathVariable("stop_id") Integer stopId){
        return stopService.getPartTimetable(stopId);
    }
}
