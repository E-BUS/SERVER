package EBus.EBusback.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import EBus.EBusback.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
