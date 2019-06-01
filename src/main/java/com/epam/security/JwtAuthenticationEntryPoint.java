package com.epam.security;

import com.epam.model.payload.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
    log.error("commence: responding with unauthorized error. Message - {}", e.getMessage());

    ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
    OutputStream out = httpServletResponse.getOutputStream();

    ErrorResponse errorResponse = new ErrorResponse(
        401, "Security error", "Not authorized");
    objectWriter.writeValue(out, errorResponse);
    out.flush();
  }

}
