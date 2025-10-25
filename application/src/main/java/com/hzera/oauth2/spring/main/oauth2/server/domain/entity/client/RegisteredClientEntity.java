package com.hzera.oauth2.spring.main.oauth2.server.domain.entity.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredClientEntity {
    private UUID id;
    private String clientId;
    private String clientSecret;
    private String name;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private int accessValidity;
    private int refreshValidity;
    private Set<String> scopes;
    private Set<String> postLogoutUris;
    private Set<String> redirectUris;
    private Set<GrantTypeEnum> grantTypes;
    private Set<ClientAuthMethodsEnum> authMethods;
    private boolean isFirstParty;
}
