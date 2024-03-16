package EBus.EBusback.domain.table.repository;

import EBus.EBusback.domain.table.entity.TimeTableDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface TimeTableDayRepository extends JpaRepository<TimeTableDay, Integer> {
    List<TimeTableDay> findByIsUpboundOrderByDepartureTimeAsc(Boolean isUpbound);
}
