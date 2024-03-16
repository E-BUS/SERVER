package EBus.EBusback.global.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

// 엔티티의 createdDate 컬럼을 자동으로 생성하기 위한 클래스
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdDate;
}
