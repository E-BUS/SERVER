package EBus.EBusback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing        // createdDate 자동으로 생성하기 위해 필요한 어노테이션
@EnableFeignClients       // open feign 클라이언트를 사용하기 위해 필요한 어노테이션
@SpringBootApplication
public class EBusBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(EBusBackApplication.class, args);
	}

}
