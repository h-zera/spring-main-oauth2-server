package com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.client;

public enum GrantTypeEnum {
    authorization_code,
    client_credentials,
    refresh_token,
    device_code,
    jwt_bearer,
    token_exchange;
}
