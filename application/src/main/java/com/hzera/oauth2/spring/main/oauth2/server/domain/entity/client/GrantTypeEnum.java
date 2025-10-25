package com.hzera.oauth2.spring.main.oauth2.server.domain.entity.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GrantTypeEnum {
    AUTHORIZATION_CODE("authorization_code"),
    CLIENT_CREDENTIALS("client_credentials"),
    REFRESH_TOKEN("refresh_token"),
    DEVICE_CODE("device_code"),
    JWT_BEARER("jwt_bearer"),
    TOKEN_EXCHANGE("token_exchange");

    private final String  value;
}
