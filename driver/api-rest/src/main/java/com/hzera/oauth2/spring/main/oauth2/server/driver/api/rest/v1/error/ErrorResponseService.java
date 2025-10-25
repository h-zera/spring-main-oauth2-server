package com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.openapi.model.ErrorResourceResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

import java.io.IOException;

@RequiredArgsConstructor
public abstract class ErrorResponseService {
    private final ObjectMapper objectMapper;

    protected void writeResponse(
            int status,
            HttpServletResponse response,
            ErrorResourceResponse error
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status);

        var responseBody = objectMapper.writeValueAsString(error);

        response.getOutputStream().write(responseBody.getBytes());
    }
}
