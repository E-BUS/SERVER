package EBus.EBusback.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import EBus.EBusback.domain.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
