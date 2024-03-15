package EBus.EBusback.domain.table.entity;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Route {
	RCB("연구협력관"),
	HANWOORI("한우리집");

	private final String name;

	public static Route from(String name) {
		return Arrays.stream(Route.values())
			.filter(route -> route.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid route: " + name));
	}

	public static String to(Route route) {
		return route.name;
	}
}
