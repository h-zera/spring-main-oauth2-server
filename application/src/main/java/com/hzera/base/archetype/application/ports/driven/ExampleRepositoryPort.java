package com.hzera.base.archetype.application.ports.driven;

import com.hzera.base.archetype.common.interfaces.SNACrudRepository;
import com.hzera.base.archetype.domain.entity.ExampleEntity;

public interface ExampleRepositoryPort extends SNACrudRepository<ExampleEntity, Long> {
}
