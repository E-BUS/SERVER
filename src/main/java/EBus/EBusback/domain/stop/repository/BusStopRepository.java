package EBus.EBusback.domain.stop.repository;

import EBus.EBusback.domain.stop.entity.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStopRepository extends JpaRepository<BusStop, Integer> {
    BusStop findByStopId(Integer stopId);
}
