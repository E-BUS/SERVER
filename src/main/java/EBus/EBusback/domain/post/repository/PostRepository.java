package EBus.EBusback.domain.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import EBus.EBusback.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllByIsSuggestionOrderByCreatedDateDesc(Boolean isSuggestion);
}
