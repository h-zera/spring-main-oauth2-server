package com.hzera.oauth2.spring.main.oauth2.server.application.services;

import com.hzera.oauth2.spring.main.oauth2.server.application.ports.driven.ClientRepositoryPort;
import com.hzera.oauth2.spring.main.oauth2.server.application.ports.driver.ClientServicePort;
import com.hzera.oauth2.spring.main.oauth2.server.domain.entity.client.RegisteredClientEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceUseCase implements ClientServicePort {
    private final ClientRepositoryPort clientRepository;

    @Override
    public Optional<RegisteredClientEntity> findById(UUID id) {
        return clientRepository.findById(id);
    }

    @Override
    public Optional<RegisteredClientEntity> findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId);
    }

    @Override
    public void save(RegisteredClientEntity registeredClient) {
        clientRepository.save(registeredClient);
    }
}
