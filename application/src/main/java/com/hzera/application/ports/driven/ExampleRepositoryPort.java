package com.hzera.application.ports.driven;

import com.hzera.common.interfaces.SNACrudRepository;
import com.hzera.domain.entity.ExampleEntity;

public interface ExampleRepositoryPort extends SNACrudRepository<ExampleEntity, Long> {
}
