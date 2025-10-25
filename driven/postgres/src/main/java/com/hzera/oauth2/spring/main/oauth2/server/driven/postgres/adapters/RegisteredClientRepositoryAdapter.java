package com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.adapters;

import com.hzera.oauth2.spring.main.oauth2.server.application.ports.driven.ClientRepositoryPort;
import com.hzera.oauth2.spring.main.oauth2.server.domain.entity.client.RegisteredClientEntity;
import com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.mappers.ClientMapper;
import com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.repository.client.RegisteredClientMOJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisteredClientRepositoryAdapter implements ClientRepositoryPort {
    private final RegisteredClientMOJpaRepository registeredClientJpa;

    private final ClientMapper clientMapper;

    @Override
    public Optional<RegisteredClientEntity> findById(UUID id) {
        var clientMO = registeredClientJpa.findById(id);

        return clientMapper.toOptionalEntity(clientMO);
    }

    @Override
    public Optional<RegisteredClientEntity> findByClientId(String clientId) {
        var clientMO = registeredClientJpa.findRegisteredClientMOByClientId(clientId);

        return clientMapper.toOptionalEntity(clientMO);
    }

    @Override
    public void save(RegisteredClientEntity registeredClientEntity) {
        var clientMO = clientMapper.toModel(registeredClientEntity);

        registeredClientJpa.save(clientMO);
    }
}
