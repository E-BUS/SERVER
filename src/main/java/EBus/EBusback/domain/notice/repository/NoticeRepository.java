package EBus.EBusback.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import EBus.EBusback.domain.notice.entity.Notice;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByOrderByCreatedDateDesc();
}
