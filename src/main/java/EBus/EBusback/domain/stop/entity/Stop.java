package EBus.EBusback.domain.stop.entity;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Stop {
	MAIN_GATE("정문"),
	POSCO("포스코관"),
	ENGINEERING("공대삼거리"),
	DORMITORY("기숙사삼거리"),
	RCB("연구협력관"),
	HANWOORI("한우리집"),
	E_HOUSE("이하우스"),
	ART_DESIGN("조형대삼거리");

	private final String name;

	public static Stop from(String name) {
		return Arrays.stream(Stop.values())
			.filter(stop -> stop.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid bus stop: " + name));
	}

	public static String to(Stop stop) {
		return stop.name;
	}
}
