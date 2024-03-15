package EBus.EBusback.domain.heart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import EBus.EBusback.domain.heart.entity.Heart;
import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.domain.post.entity.Post;

public interface HeartRepository extends JpaRepository<Heart, Long> {

	Optional<Heart> findByMemberAndPost(Member member, Post post);

	List<Heart> findByPost(Post post);
}
