package com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.repository.client;

import com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.client.RegisteredClientMO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegisteredClientMOJpaRepository extends JpaRepository<RegisteredClientMO, UUID> {
    Optional<RegisteredClientMO> findRegisteredClientMOByClientId(String clientId);
}
