package EBus.EBusback.domain.stop.repository;

import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.domain.stop.entity.BusStop;
import EBus.EBusback.domain.stop.entity.PinStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PinStopRepository extends JpaRepository<PinStop, Long> {
    Optional<PinStop> findByMemberAndStop(Member member, BusStop stop);
}
