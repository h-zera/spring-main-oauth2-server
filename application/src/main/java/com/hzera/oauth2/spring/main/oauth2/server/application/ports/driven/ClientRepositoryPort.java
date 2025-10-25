package com.hzera.oauth2.spring.main.oauth2.server.application.ports.driven;

import com.hzera.oauth2.spring.main.oauth2.server.domain.entity.client.RegisteredClientEntity;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepositoryPort {
    Optional<RegisteredClientEntity> findById(UUID id);

    Optional<RegisteredClientEntity> findByClientId(String clientId);

    void save(RegisteredClientEntity registeredClientEntity);
}
