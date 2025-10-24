package com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.config.security;

import com.hzera.oauth2.spring.main.oauth2.server.application.ports.driver.ClientServicePort;
import com.hzera.oauth2.spring.main.oauth2.server.driver.api.rest.v1.mappers.client.RegisteredClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@RequiredArgsConstructor
public class RegisteredClientProvider implements RegisteredClientRepository {
    private final ClientServicePort clientService;

    private final RegisteredClientMapper clientMapper;

    @Override
    public void save(RegisteredClient registeredClient) {
        var clientEntity = clientMapper.toEntity(registeredClient);

        clientService.save(clientEntity);
    }

    @Override
    public RegisteredClient findById(String id) {
        var client = clientService.findById(id);

        return client.map(clientMapper::toSecurity).orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var client = clientService.findByClientId(clientId);

        return client.map(clientMapper::toSecurity).orElse(null);
    }
}
