package com.epam.security;

import com.epam.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JwtTokenProvider {

  @Value("${app.security.jwtSecret}")
  private String jwtSecret;

  @Value("${app.security.jwtExpirationInMs}")
  private int jwtExpirationInMs;

  public String generateToken(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Date timeNow = new Date();

    return Jwts.builder()
        .setSubject(user.getId())
        .setIssuedAt(timeNow)
        .setExpiration(new Date(timeNow.getTime() + jwtExpirationInMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public String getUserIdFromJWT(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody();

    return claims.getSubject();
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (ExpiredJwtException exception) {
      log.warn("Request to parse expired JWT : {} failed : {}", authToken, exception.getMessage());
    } catch (UnsupportedJwtException exception) {
      log.warn("Request to parse unsupported JWT : {} failed : {}", authToken,
          exception.getMessage());
    } catch (MalformedJwtException exception) {
      log.warn("Request to parse invalid JWT : {} failed : {}", authToken, exception.getMessage());
    } catch (SignatureException exception) {
      log.warn("Request to parse JWT with invalid signature : {} failed : {}", authToken,
          exception.getMessage());
    } catch (IllegalArgumentException exception) {
      log.warn("Request to parse empty or null JWT : {} failed : {}", authToken,
          exception.getMessage());
    }
    return false;
  }

}
