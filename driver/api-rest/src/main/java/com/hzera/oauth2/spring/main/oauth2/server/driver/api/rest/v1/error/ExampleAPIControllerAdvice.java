package com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.error;

import com.hzera.oauth2.spring.main.oauth2.server.application.exceptions.ExampleNotFoundException;
import com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.openapi.model.ErrorResource;
import com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.openapi.model.ErrorResourceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@Slf4j
@RestControllerAdvice
public class ExampleAPIControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ExampleNotFoundException.class)
    public ErrorResourceResponse handleExampleNotFoundException(ExampleNotFoundException exception) {
        ErrorResource errorResource = ErrorResource.builder()
                .code(exception.getErrorCode())
                .description(exception.getMessage())
                .details(Collections.singletonList(exception.getMessage()))
                .build();

        return new ErrorResourceResponse.Builder().error(errorResource).build();
    }
}
