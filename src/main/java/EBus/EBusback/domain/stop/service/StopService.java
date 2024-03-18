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
        List<PinStop> pinStops = pinStopRepository.findAllByMember(member);

        for (PinStop pinstop : pinStops){
            if (pinstop.getIsValid()) {
                stopId.add(pinstop.getStop().getStopId());
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
        int m = stopTimetable.getM();
        int n = stopTimetable.getN();

        // 상하행 리스트에서 index 값으로 dto 조회하기 전에 index 값이 리스트.size() 이하인지 확인부터 해야 됨.
        // 해당 정류장에서 현재 시각과 출발 시간이 가장 가까운 시간
        TimeResponseDto closestUp;
        TimeResponseDto closestDown;

        if (ups.size()>m){
            closestUp = ups.get(m);
        }
        else{
            closestUp = null;
        }

        if (downs.size()>n){
            closestDown = downs.get(n);
        }
        else{
            closestDown = null;
        }

        return new WholeTimetableResDto(ups, downs, closestUp, closestDown);
    }

    // 핀한 정류장 일부 시간표 조회
    public PinnedStopTimeResDto getPartTimetable(Integer stopId) {
        Member member = SecurityUtil.getCurrentUser();
        if (member == null)
            throw new ResponseStatusException(ErrorCode.NON_LOGIN.getStatus(), ErrorCode.NON_LOGIN.getMessage());
        StopTimetable stopTimetable = getStopTimetable(stopId);
        int dayOfWeekNumber = stopTimetable.getDayOfWeekNumber();
        List<TimeResponseDto> ups = stopTimetable.getUps();
        List<TimeResponseDto> downs = stopTimetable.getDowns();
        int m = stopTimetable.getM();
        int n = stopTimetable.getN();
        LocalTime now = LocalTime.now();

        List<TimeResponseDto> closeUps = new ArrayList<>();
        List<TimeResponseDto> closeDowns = new ArrayList<>();

        /*
        1: 정문 MAIN_GATE
        2: 포스코관 POSCO
        3: 공대삼거리 ENGINEERING
        4: 기숙사삼거리 DORMITORY
        5: 연구협력관 RCB
        6: 한우리집 HANWOORI
        7: 이하우스 E_HOUSE
        8: 조형대삼거리 ART_DESIGN
         */
        // m번째 index, m+1, m+2 가 필요
        // stopId가 5 또는 7인 경우 closeUps가 비어 있도록 함
        // stopId가 6인 경우 주간에만 closeUps가 비어 있도록 함
        if (stopId == 5 | stopId == 7) {
        }
        else if (stopId == 6){
            if ((dayOfWeekNumber<6)&&(now.isBefore(LocalTime.of( 21, 10, 0)))){
            }
            else if ((dayOfWeekNumber==6)&&(now.isBefore(LocalTime.of( 19, 10, 0)))){
            }
            else {
                for (int p = m; p < m + 3; p++) {
                    if (ups.size() > p) {
                        closeUps.add(ups.get(p));
                    }
                }
            }
        }
        else {
            for (int p = m; p < m + 3; p++) {
                if (ups.size() > p) {
                    closeUps.add(ups.get(p));
                }
            }
        }

        // stopId가 1 또는 8인 경우 closeDowns가 비어 있도록 함
        if (stopId == 1 | stopId == 8) {
            }
        else {
            for (int q = n; q < n + 3; q++) {
                if (downs.size() > q) {
                    closeDowns.add(downs.get(q));
                }
            }
        }
        return new PinnedStopTimeResDto(closeUps, closeDowns);
    }

    // 시간표 데이터 전달 dto
    @Getter
    public static class StopTimetable {
        private int dayOfWeekNumber;
        private List<TimeResponseDto> ups;
        private List<TimeResponseDto> downs;
        private int m;
        private int n;

        @Builder
        public StopTimetable (int dayOfWeekNumber, List<TimeResponseDto> ups, List<TimeResponseDto> downs, int m, int n){
            this.dayOfWeekNumber = dayOfWeekNumber;
            this.ups = ups;
            this.downs = downs;
            this.m = m;
            this.n = n;
        }
    }

    // stopId에 따른 시간표 데이터 조회
    public StopTimetable getStopTimetable(Integer stopId){
        // 비어 있는 상행 리스트
        List<TimeResponseDto> ups = new ArrayList<>();
        // 비어 있는 하행 리스트
        List<TimeResponseDto> downs = new ArrayList<>();

        // 현재 요일 구하기 (월:1, ..., 일:7)+
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
                // 주간 연협 상행 (stopId: 1,2,3,4,5)
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

                    if(stopId==1|stopId==2|stopId==3|stopId==4|stopId==5){
                        TimeResponseDto timeDto = TimeResponseDto.builder()
                                .route(dayUp.getRoute().toString())
                                .time(dayUp.getDepartureTime().plusMinutes(plus1))
                                .build();
                        ups.add(timeDto);
                    }
                }

                // 주간 한우리 상행  (stopId: 1,2,3,6)
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

                    if(stopId==1|stopId==2|stopId==3|stopId==6){
                        TimeResponseDto timeDto = TimeResponseDto.builder()
                                .route(dayUp.getRoute().toString())
                                .time(dayUp.getDepartureTime().plusMinutes(plus1))
                                .build();
                        ups.add(timeDto);
                    }
                }
            }

            for (TimeTableDay dayDown : dayDowns) {
                int plus2 = 0;
                // 주간 연협 하행 (stopId: 1,2,3,4,5)
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

                    if(stopId==1|stopId==2|stopId==3|stopId==4|stopId==5){
                        TimeResponseDto timeDto = TimeResponseDto.builder()
                                .route("MAIN_GATE")
                                .time(dayDown.getDepartureTime().plusMinutes(plus2))
                                .build();
                        downs.add(timeDto);
                    }
                }

                // 주간 한우리 상행 (stopId: 1,2,3,6)
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

                    if(stopId==1|stopId==2|stopId==3|stopId==6){
                        TimeResponseDto timeDto = TimeResponseDto.builder()
                                .route("MAIN_GATE")
                                .time(dayDown.getDepartureTime().plusMinutes(plus2))
                                .build();
                        downs.add(timeDto);
                    }
                }
            }

            // 평일 야간 상행 (stopId: 2,6,7,8)
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

                if(stopId==2|stopId==6|stopId==7|stopId==8){
                    TimeResponseDto timeDto = TimeResponseDto.builder()
                            .route("E_HOUSE")
                            .time(weekdayNightUp.getDepartureTime().plusMinutes(plus3))
                            .build();
                    ups.add(timeDto);
                }
            }

            // 평일 야간 하행 (stopId: 2,6,7,8)
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

                if(stopId==2|stopId==6|stopId==7|stopId==8){
                    TimeResponseDto timeDto = TimeResponseDto.builder()
                            .route("ART_DESIGN")
                            .time(weekdayNightDown.getDepartureTime().plusMinutes(plus4))
                            .build();
                    downs.add(timeDto);
                }
            }
        }

        // 토 : 야간 상하행 전체
        else if (dayOfWeekNumber == 6) {
            // 토요일 야간 상행 (stopId: 2,6,7,8)
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

                if(stopId==2|stopId==6|stopId==7|stopId==8){
                    TimeResponseDto timeDto = TimeResponseDto.builder()
                            .route("E_HOUSE")
                            .time(satNightUp.getDepartureTime().plusMinutes(plus5))
                            .build();
                    ups.add(timeDto);
                }
            }

            // 토요일 야간 하행 (stopId: 2,6,7,8)
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

                if(stopId==2|stopId==6|stopId==7|stopId==8){
                    TimeResponseDto timeDto = TimeResponseDto.builder()
                            .route("ART_DESIGN")
                            .time(satNightDown.getDepartureTime().plusMinutes(plus6))
                            .build();
                    downs.add(timeDto);
                }
            }
        }

        // 현재 시간
        LocalTime now = LocalTime.now();

        int m = 0;
        int n = 0;

        // m은 현재 시간 이후에 출발하는 첫 시간표의 index
        if (!(ups.isEmpty())){
            while (ups.get(m).getTime().isBefore(now) && m < ups.size()) {
                m++;
            }
        }

        // n은 현재 시간 이후에 출발하는 첫 시간표의 index
        if (!(downs.isEmpty())){
            while (downs.get(n).getTime().isBefore(now) && n < downs.size()) {
                n++;
            }
        }

        return new StopTimetable(dayOfWeekNumber, ups, downs, m, n);
    }
}
