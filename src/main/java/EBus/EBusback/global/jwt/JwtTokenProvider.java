package EBus.EBusback.global.jwt;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import EBus.EBusback.domain.member.entity.Member;
import EBus.EBusback.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

	@Autowired
	private MemberRepository memberRepository;

	private Key key;
	private Long accessTokenValidTime = Duration.ofDays(7).toMillis();

	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String createAccessToken(Member member) {
		Claims claims = Jwts.claims();
		claims.put("memberId", member.getMemberId());

		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + accessTokenValidTime))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public String resolveToken(HttpServletRequest request) {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorization == null || !authorization.startsWith("Bearer")) {
			return null;
		}
		return authorization.substring(7);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			log.debug("Success: Token validation");
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT is empty.", e);
		}
		return false;
	}

	public Authentication getAuthentication(String token) {
		try {
			Long memberId = parseClaims(token).get("memberId", Long.class);
			Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new UsernameNotFoundException("user not found: " + memberId));
			return new UsernamePasswordAuthenticationToken(member, "");
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (JwtException e) {
			log.info("Invalid JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT is empty.", e);
		}
		return null;
	}

	public Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}
