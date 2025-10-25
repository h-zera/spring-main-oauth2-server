package com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GrantTypeMOEnum {
    authorization_code("authorization_code"),
    client_credentials("client_credentials"),
    refresh_token("refresh_token"),
    device_code("device_code"),
    jwt_bearer("jwt_bearer"),
    token_exchange("token_exchange");

    private final String value;
}
