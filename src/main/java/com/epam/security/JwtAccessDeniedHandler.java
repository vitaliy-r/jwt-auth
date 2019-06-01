package com.epam.security;

import com.epam.model.payload.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      AccessDeniedException e) throws IOException {
    log.error("handle: responding with access denied error. Message - {}", e.getMessage());

    ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
    OutputStream out = httpServletResponse.getOutputStream();

    ErrorResponse errorResponse = new ErrorResponse(
        403, "Security error", "Access denied");
    objectWriter.writeValue(out, errorResponse);
    out.flush();
  }

}
