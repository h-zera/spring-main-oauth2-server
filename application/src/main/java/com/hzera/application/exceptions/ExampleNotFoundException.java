package com.hzera.application.exceptions;

import com.hzera.common.domain.HZeraBusinessException;

public class ExampleNotFoundException extends HZeraBusinessException {

    public ExampleNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }
}
