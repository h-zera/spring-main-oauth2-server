package com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.error.client;

import com.hzera.oauth2.spring.main.oauth2.server.application.exceptions.client.RegisteredClientNotFound;
import com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.openapi.model.ErrorResourceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ClientAPIControllerAdvice {
    private final ClientErrorResponseService clientErrorResponseService;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RegisteredClientNotFound.class)
    public ErrorResourceResponse handleExampleNotFoundException(RegisteredClientNotFound exception) {
        return clientErrorResponseService.handleClientNotFoundException(exception);
    }
}
