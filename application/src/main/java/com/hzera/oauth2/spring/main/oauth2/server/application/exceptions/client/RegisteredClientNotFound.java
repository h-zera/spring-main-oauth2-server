package com.hzera.oauth2.spring.main.oauth2.server.application.exceptions.client;

import com.hzera.oauth2.spring.main.oauth2.server.common.domain.HZeraBusinessException;

public class RegisteredClientNotFound extends HZeraBusinessException {
    private static final String ERROR_CODE = "CLIENT_NOT_FOUND";

    public RegisteredClientNotFound(String message) {
        super(message, ERROR_CODE);
    }
}
