package com.hzera.oauth2.spring.oauth2.server.application.ports.driven;

import com.hzera.oauth2.spring.oauth2.server.common.interfaces.SNACrudRepository;
import com.hzera.oauth2.spring.oauth2.server.domain.entity.ExampleEntity;

public interface ExampleRepositoryPort extends SNACrudRepository<ExampleEntity, Long> {
}
