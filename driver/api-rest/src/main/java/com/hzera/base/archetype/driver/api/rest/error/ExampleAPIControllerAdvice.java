package com.hzera.base.archetype.driver.api.rest.error;

import com.hzera.base.archetype.application.exceptions.ExampleNotFoundException;
import com.hzera.base.archetype.driver.api.rest.openapi.model.ErrorResource;
import com.hzera.base.archetype.driver.api.rest.openapi.model.ErrorResourceResponse;
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
