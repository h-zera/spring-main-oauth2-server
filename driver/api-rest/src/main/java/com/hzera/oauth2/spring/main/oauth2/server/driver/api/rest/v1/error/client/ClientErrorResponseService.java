package com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.error.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzera.oauth2.spring.main.oauth2.server.application.exceptions.client.RegisteredClientNotFound;
import com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.error.ErrorResponseService;
import com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.openapi.model.ErrorResource;
import com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.openapi.model.ErrorResourceResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class ClientErrorResponseService extends ErrorResponseService {
    public ClientErrorResponseService(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    public ErrorResourceResponse handleClientNotFoundException(RegisteredClientNotFound exception) {
        ErrorResource errorResource = ErrorResource.builder()
                .code(exception.getErrorCode())
                .description(exception.getMessage())
                .details(Collections.singletonList(exception.getMessage()))
                .build();

        return new ErrorResourceResponse.Builder().error(errorResource).build();
    }

    public void writeClientNotFoundResponse(
            int status,
            HttpServletResponse response,
            RegisteredClientNotFound exception
    ) throws IOException {
        ErrorResourceResponse error = handleClientNotFoundException(exception);

        writeResponse(status, response, error);
    }

    public void writeClientNotFoundResponse(
            int status,
            HttpServletResponse response,
            RegisteredClientNotFound exception,
            List<String> details
    ) throws IOException {
        ErrorResourceResponse error = handleClientNotFoundException(exception);

        assert error.getError() != null;
        error.getError().setDetails(details);

        writeResponse(status, response, error);
    }
}
