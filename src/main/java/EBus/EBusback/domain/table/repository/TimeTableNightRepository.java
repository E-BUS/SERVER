package EBus.EBusback.domain.table.repository;

import EBus.EBusback.domain.table.entity.TimeTableNight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeTableNightRepository extends JpaRepository<TimeTableNight, Integer> {
    List<TimeTableNight> findByIsUpboundAndIsWeekday(Boolean isUpbound, Boolean isWeekday);
    List<TimeTableNight> findByIsUpbound(Boolean isUpbound);
}
