package com.hzera.oauth2.spring.main.oauth2.server.application.services;

import com.hzera.oauth2.spring.main.oauth2.server.application.ports.driver.ClientServicePort;
import com.hzera.oauth2.spring.main.oauth2.server.domain.entity.client.ClientAuthMethodsEnum;
import com.hzera.oauth2.spring.main.oauth2.server.domain.entity.client.GrantTypeEnum;
import com.hzera.oauth2.spring.main.oauth2.server.domain.entity.client.RegisteredClientEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceUseCase implements ClientServicePort {
    @Override
    public Optional<RegisteredClientEntity> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<RegisteredClientEntity> findByClientId(String clientId) {
        if (!clientId.equals("test-client")) return Optional.empty();

        var testClient = RegisteredClientEntity.builder()
                .id(UUID.randomUUID())
                .clientId("test-client")
                .clientSecret("$2a$12$AvzhZEXGkM.aC583qYFY7.whyUmaxRMFlXz847ElSKQ3Jh0D8lzxq")
                .scopes(Set.of("cdn:sign"))
                .authMethods(Set.of(ClientAuthMethodsEnum.CLIENT_SECRET_BASIC))
                .accessValidity(3600)
                .refreshValidity(86400)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .grantTypes(Set.of(GrantTypeEnum.CLIENT_CREDENTIALS))
                .isFirstParty(true)
                .build();

        return Optional.of(testClient);
    }

    @Override
    public void save(RegisteredClientEntity registeredClient) {

    }
}
