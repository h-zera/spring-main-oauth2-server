package com.hzera.oauth2.spring.main.oauth2.server.application.exceptions;

import com.hzera.oauth2.spring.main.oauth2.server.common.domain.HZeraBusinessException;

public class ExampleNotFoundException extends HZeraBusinessException {

    public ExampleNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }
}
