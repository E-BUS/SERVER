package EBus.EBusback.domain.lostItem.repository;

import EBus.EBusback.domain.lostItem.entity.LostItem;
import EBus.EBusback.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LostItemRepository extends JpaRepository<LostItem, Long> {
    List<LostItem> findByTitleContaining(String keyword);

    Optional<LostItem> findByItemIdAndWriter(Long itemId, Member writer);
}
