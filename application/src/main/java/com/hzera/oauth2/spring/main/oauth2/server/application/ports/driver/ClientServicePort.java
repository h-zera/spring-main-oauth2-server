package com.hzera.oauth2.spring.main.oauth2.server.application.ports.driver;

import com.hzera.oauth2.spring.main.oauth2.server.domain.entity.client.RegisteredClientEntity;

import java.util.Optional;
import java.util.UUID;

public interface ClientServicePort {
    Optional<RegisteredClientEntity> findById(UUID id);

    default Optional<RegisteredClientEntity> findById(String id) {
        return this.findById(UUID.fromString(id));
    }

    Optional<RegisteredClientEntity> findByClientId(String clientId);

    void save(RegisteredClientEntity registeredClient);
}
