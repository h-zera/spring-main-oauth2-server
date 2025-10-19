package com.hzera.base.archetype.driver.api.rest.adapters;

import com.hzera.base.archetype.application.ports.driver.ExampleServicePort;
import com.hzera.base.archetype.driver.api.rest.mappers.ExampleDTOMapper;
import com.hzera.base.archetype.driver.api.rest.openapi.ExamplesApi;
import com.hzera.base.archetype.driver.api.rest.openapi.model.ExampleRequest;
import com.hzera.base.archetype.driver.api.rest.openapi.model.ExampleResourceCollectionResponse;
import com.hzera.base.archetype.driver.api.rest.openapi.model.ExampleResourceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class ExampleControllerAdapter implements ExamplesApi {
    private final ExampleServicePort exampleService;
    private final ExampleDTOMapper exampleDTOMapper;

    @Override
    public ResponseEntity<ExampleResourceCollectionResponse> getExampleCollection(Integer page, Integer size, String sort) {
        var exampleEntities = exampleService.getAllExamples(page, size, sort);

        var exampleResourceCollection = exampleDTOMapper.toExampleResourceCollectionResponse(exampleEntities);

        return ResponseEntity.ok(exampleResourceCollection);
    }

    @Override
    public ResponseEntity<ExampleResourceResponse> getExample(Long id) {
        var example = exampleService.getExample(id);

        if (example.isEmpty()) return ResponseEntity.notFound().build();

        var exampleResponse = exampleDTOMapper.toResponse(example.get());

        return ResponseEntity.ok(exampleResponse);
    }

    @Override
    public ResponseEntity<ExampleResourceResponse> createExample(ExampleRequest request) {
        var exampleEntity = exampleDTOMapper.from(request);

        var exampleSaved = exampleService.createExample(exampleEntity);

        var exampleResponse = exampleDTOMapper.toResponse(exampleSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(exampleResponse);
    }

    @Override
    public ResponseEntity<ExampleResourceResponse> updateExample(Long id, ExampleRequest request) {
        var exampleEntity = exampleDTOMapper.from(request);

        var exampleSaved = exampleService.updateExample(id, exampleEntity);

        var exampleResponse = exampleDTOMapper.toResponse(exampleSaved);

        return ResponseEntity.ok(exampleResponse);
    }

    @Override
    public ResponseEntity<Void> deleteExample(Long id) {
        exampleService.deleteExample(id);

        return ResponseEntity.ok().build();
    }
}
