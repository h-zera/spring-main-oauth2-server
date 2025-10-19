package com.hzera.oauth2.spring.oauth2.server.application.ports.driver;

import com.hzera.oauth2.spring.oauth2.server.application.exceptions.ExampleNotFoundException;
import com.hzera.oauth2.spring.oauth2.server.common.domain.HZeraPage;
import com.hzera.oauth2.spring.oauth2.server.domain.entity.ExampleEntity;

import java.util.Optional;

public interface ExampleServicePort {

    HZeraPage<ExampleEntity> getAllExamples(Integer pageNumber, Integer pageSize, String sort);

    Optional<ExampleEntity> getExample(Long id) throws ExampleNotFoundException;

    ExampleEntity createExample(ExampleEntity example);

    ExampleEntity updateExample(Long id, ExampleEntity exampleUpdate);

    void deleteExample(Long id);

}
