package EBus.EBusback.domain.stop.service;

import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.domain.stop.dto.*;
import EBus.EBusback.domain.stop.entity.BusStop;
import EBus.EBusback.domain.stop.entity.PinStop;
import EBus.EBusback.domain.stop.repository.BusStopRepository;
import EBus.EBusback.domain.stop.repository.PinStopRepository;
import EBus.EBusback.domain.table.entity.TimeTableDay;
import EBus.EBusback.domain.table.entity.TimeTableNight;
import EBus.EBusback.domain.table.repository.TimeTableDayRepository;
import EBus.EBusback.domain.table.repository.TimeTableNightRepository;
import EBus.EBusback.global.SecurityUtil;
import EBus.EBusback.global.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StopService {
    private final PinStopRepository pinStopRepository;
    private final BusStopRepository busStopRepository;
    private final TimeTableDayRepository timeTableDayRepository;
    private final TimeTableNightRepository timeTableNightRepository;

    // 정류장 핀 등록/취소
    public StopPinResDto createOrRemovePin(Member member, StopPinReqDto stopPinReqDto) {
        if (member == null)
            throw new RuntimeException("사용자를 찾을 수 없습니다.");

        List<Integer> stopId = new ArrayList<>();

        for (int i=0; i<8; i++){
            BusStop stop = busStopRepository.findByStopId(i+1);
            PinStop pinStop = pinStopRepository.findByMemberAndStop(member, stop)
                    .orElseGet(() -> pinStopRepository.save(PinStop.builder().member(member).stop(stop).build()));
            pinStop.updatePinStop(stopPinReqDto.getStopPinned().get(i));

            if(pinStop.getIsValid()){
                stopId.add(i+1);
            }
        }
        return StopPinResDto.builder()
                .stopId(stopId)
                .build();
    }

    // 핀한 정류장 리스트 조회
    public StopPinResDto getPinnedStopList(Member member) {
        List<Integer> stopId = new ArrayList<>();
        for (int i=0; i<8; i++){
            BusStop stop = busStopRepository.findByStopId(i+1);
            PinStop pinStop = pinStopRepository.findByMemberAndStop(member, stop)
                    .orElseThrow(() -> new EntityNotFoundException("해당 핀 정보가 존재하지 않습니다."));
            if(pinStop.getIsValid()){
                stopId.add(i+1);
            }
        }
        return StopPinResDto.builder()
                .stopId(stopId)
                .build();
    }

    // 특정 정류장 전체 시간표 조회
    public WholeTimetableResDto getWholeTimetable(Integer stopId) {
        StopTimetable stopTimetable = getStopTimetable(stopId);
        List<TimeResponseDto> ups = stopTimetable.getUps();
        List<TimeResponseDto> downs = stopTimetable.getDowns();
        int i = stopTimetable.getI();

        // 해당 정류장에서 현재 시각과 출발 시간이 가장 가까운 시간
        TimeResponseDto closestUp = new TimeResponseDto();
        TimeResponseDto closestDown = new TimeResponseDto();
        closestUp = ups.get(i);
        closestDown = downs.get(i);

        return new WholeTimetableResDto(ups, downs, closestUp, closestDown);
    }

    // 핀한 정류장 일부 시간표 조회
    public PinnedStopTimeResDto getPartTimetable(Integer stopId) {
        Member member = SecurityUtil.getCurrentUser();
        if (member == null)
            throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());
        StopTimetable stopTimetable = getStopTimetable(stopId);
        List<TimeResponseDto> ups = stopTimetable.getUps();
        List<TimeResponseDto> downs = stopTimetable.getDowns();
        int i = stopTimetable.getI();

        List<TimeResponseDto> closeUps = new ArrayList<>();
        List<TimeResponseDto> closeDowns = new ArrayList<>();
        for (int j=i; j<i+3; j++){
            closeUps.add(ups.get(j));
            closeDowns.add(downs.get(j));
        }
        return new PinnedStopTimeResDto(closeUps, closeDowns);
    }

    @Getter
    public static class StopTimetable {
        private List<TimeResponseDto> ups;
        private List<TimeResponseDto> downs;
        private int i;

        @Builder
        public StopTimetable (List<TimeResponseDto> ups, List<TimeResponseDto> downs, int i){
            this.ups = ups;
            this.downs = downs;
            this.i = i;
        }
    }

    public StopTimetable getStopTimetable(Integer stopId){
        // 비어 있는 상행 리스트
        List<TimeResponseDto> ups = new ArrayList<>();
        // 비어 있는 하행 리스트
        List<TimeResponseDto> downs = new ArrayList<>();

        // 현재 요일 구하기 (월:1, ..., 일:7)
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        int dayOfWeekNumber = dayOfWeek.getValue();

        // 주간 상행. 연협행, 한우리행 섞여있고, 시간순으로 정렬해서 조회
        List<TimeTableDay> dayUps = timeTableDayRepository.findByIsUpboundOrderByDepartureTimeAsc(Boolean.TRUE);

        // 주간 하행. 연협행, 한우리행 섞여있고, 시간순으로 정렬해서 조회
        List<TimeTableDay> dayDowns = timeTableDayRepository.findByIsUpboundOrderByDepartureTimeAsc(Boolean.FALSE);

        // 야간 isWeekday=1 상행
        List<TimeTableNight> weekdayNightUps = timeTableNightRepository.findByIsUpboundAndIsWeekday(Boolean.TRUE, Boolean.TRUE);
        // 야간 isWeekday=1 하행
        List<TimeTableNight> weekdayNightDowns = timeTableNightRepository.findByIsUpboundAndIsWeekday(Boolean.FALSE, Boolean.TRUE);
        // 야간 상행 전체 (토요일)
        List<TimeTableNight> satNightUps = timeTableNightRepository.findByIsUpbound(Boolean.TRUE);
        // 야간 하행 전체 (토요일)
        List<TimeTableNight> satNightDowns = timeTableNightRepository.findByIsUpbound(Boolean.FALSE);

        // 월~금 : 주간 상하행 + 야간 isWeekday=1 상하행
        if (dayOfWeekNumber<6) {
            // 주간 상행
            for (TimeTableDay dayUp : dayUps) {
                int plus1 = 0;
                if (Objects.equals(dayUp.getRoute().toString(), "RCB")) {
                    if (stopId == 1) {
                        plus1 = 0;
                    }
                    else if (stopId == 2) {
                        plus1 = 2;
                    }
                    else if (stopId == 3) {
                        plus1 = 4;
                    }
                    else if (stopId == 4) {
                        plus1 = 6;
                    }
                    else if (stopId == 5) {
                        plus1 = 7;
                    }
                    TimeResponseDto timeDto = TimeResponseDto.builder()
                            .route(dayUp.getRoute().toString())
                            .time(dayUp.getDepartureTime().plusMinutes(plus1))
                            .build();
                    ups.add(timeDto);
                }
                else if (Objects.equals(dayUp.getRoute().toString(), "HANWOORI")) {
                    if (stopId == 1) {
                        plus1 = 0;
                    }
                    else if (stopId == 2) {
                        plus1 = 2;
                    }
                    else if (stopId == 3) {
                        plus1 = 4;
                    }
                    else if (stopId == 6) {
                        plus1 = 7;
                    }

                    TimeResponseDto timeDto = TimeResponseDto.builder()
                            .route(dayUp.getRoute().toString())
                            .time(dayUp.getDepartureTime().plusMinutes(plus1))
                            .build();
                    ups.add(timeDto);
                }
            }

            // 주간 하행
            for (TimeTableDay dayDown : dayDowns) {
                int plus2 = 0;
                if (Objects.equals(dayDown.getRoute().toString(), "RCB")) {
                    if (stopId == 1) {
                        plus2 = 7;
                    }
                    else if (stopId == 2) {
                        plus2 = 5;
                    }
                    else if (stopId == 3) {
                        plus2 = 3;
                    }
                    else if (stopId == 4) {
                        plus2 = 1;
                    }
                    else if (stopId == 5) {
                        plus2 = 0;
                    }
                    TimeResponseDto timeDto = TimeResponseDto.builder()
                            .route("MAIN_GATE")
                            .time(dayDown.getDepartureTime().plusMinutes(plus2))
                            .build();
                    downs.add(timeDto);
                }
                else if (Objects.equals(dayDown.getRoute().toString(), "HANWOORI")){
                    if (stopId == 1) {
                        plus2 = 7;
                    }
                    else if (stopId == 2) {
                        plus2 = 5;
                    }
                    else if (stopId == 3) {
                        plus2 = 3;
                    }
                    else if (stopId == 6) {
                        plus2 = 0;
                    }
                    TimeResponseDto timeDto = TimeResponseDto.builder()
                            .route("MAIN_GATE")
                            .time(dayDown.getDepartureTime().plusMinutes(plus2))
                            .build();
                    downs.add(timeDto);
                }
            }

            // 평일 야간 상행
            for (TimeTableNight weekdayNightUp : weekdayNightUps) {
                int plus3 = 0;
                if (stopId == 8) {
                    plus3 = 0;
                }
                else if (stopId == 2){
                    plus3 = 2;
                }
                else if (stopId == 6){
                    plus3 = 6;
                }
                else if (stopId == 7){
                    plus3 = 7;
                }
                TimeResponseDto timeDto = TimeResponseDto.builder()
                        .route("E_HOUSE")
                        .time(weekdayNightUp.getDepartureTime().plusMinutes(plus3))
                        .build();
                ups.add(timeDto);
            }

            // 평일 야간 하행
            for (TimeTableNight weekdayNightDown : weekdayNightDowns) {
                int plus4 = 0;
                if (stopId == 8) {
                    plus4 = 7;
                }
                else if (stopId == 2){
                    plus4 = 5;
                }
                else if (stopId == 6){
                    plus4 = 1;
                }
                else if (stopId == 7){
                    plus4 = 0;
                }
                TimeResponseDto timeDto = TimeResponseDto.builder()
                        .route("ART_DESIGN")
                        .time(weekdayNightDown.getDepartureTime().plusMinutes(plus4))
                        .build();
                downs.add(timeDto);
            }
        }

        // 토 : 야간 상하행 전체
        else if (dayOfWeekNumber == 6) {
            // 토요일 야간 상행
            for (TimeTableNight satNightUp : satNightUps) {
                int plus5 = 0;
                if (stopId == 8) {
                    plus5 = 0;
                }
                else if (stopId == 2){
                    plus5 = 2;
                }
                else if (stopId == 6){
                    plus5 = 6;
                }
                else if (stopId == 7){
                    plus5 = 7;
                }
                TimeResponseDto timeDto = TimeResponseDto.builder()
                        .route("E_HOUSE")
                        .time(satNightUp.getDepartureTime().plusMinutes(plus5))
                        .build();
                ups.add(timeDto);
            }

            // 토요일 야간 하행
            for (TimeTableNight satNightDown : satNightDowns) {
                int plus6 = 0;
                if (stopId == 8) {
                    plus6 = 7;
                }
                else if (stopId == 2){
                    plus6 = 5;
                }
                else if (stopId == 6){
                    plus6 = 1;
                }
                else if (stopId == 7){
                    plus6 = 0;
                }
                TimeResponseDto timeDto = TimeResponseDto.builder()
                        .route("ART_DESIGN")
                        .time(satNightDown.getDepartureTime().plusMinutes(plus6))
                        .build();
                downs.add(timeDto);
            }
        }

        // 현재 시간
        LocalTime now = LocalTime.now();

        int i = 0;

        // i는 현재 시간 이후에 출발하는 첫 시간표의 index
        while (ups.get(i).getTime().isBefore(now) && i < ups.size()) {
            i++;
        }
        return new StopTimetable(ups, downs, i);
    }
}
