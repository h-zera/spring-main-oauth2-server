package com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.client;

public enum ClientAuthMethodsMOEnum {
    CLIENT_SECRET_BASIC,
    CLIENT_SECRET_POST,
    CLIENT_SECRET_JWT,
    PRIVATE_KEY_JWT,
    NONE,
    TLS_CLIENT_AUTH,
    SELF_SIGNED_TLS_CLIENT_AUTH
}
