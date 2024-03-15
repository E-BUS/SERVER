package EBus.EBusback.domain.lostItem.repository;

import EBus.EBusback.domain.lostItem.entity.LostItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LostItemRepository extends JpaRepository<LostItem, Long> {
}
