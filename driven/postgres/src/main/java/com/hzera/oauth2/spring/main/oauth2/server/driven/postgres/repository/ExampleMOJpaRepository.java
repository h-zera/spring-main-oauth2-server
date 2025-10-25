package com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.repository;

import com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.ExampleMO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleMOJpaRepository extends JpaRepository<ExampleMO, Long> {
}
