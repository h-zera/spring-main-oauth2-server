package com.hzera.oauth2.spring.oauth2.server.driver.api.rest.v1.mappers;

import com.hzera.oauth2.spring.oauth2.server.common.domain.HZeraPage;
import com.hzera.oauth2.spring.oauth2.server.common.rest.api.builder.HZeraPageResponseUtil;
import com.hzera.oauth2.spring.oauth2.server.domain.entity.ExampleEntity;
import com.hzera.oauth2.spring.oauth2.server.driver.api.rest.v1.openapi.model.*;
import com.hzera.oauth2.spring.oauth2.server.driver.api.rest.v1.openapi.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = { HZeraPageResponseUtil.class })
public abstract class ExampleDTOMapper {

    @Autowired
    private HZeraPageResponseUtil HZeraPageResponseUtil;

    static final ExampleDTOMapper INSTANCE = Mappers.getMapper(ExampleDTOMapper.class);

    @Mapping(target = "id", ignore = true)
    public abstract ExampleEntity from(ExampleRequest request);

    public abstract ExampleResource to(ExampleEntity entity);

    public ExampleResourceCollectionResponse toExampleResourceCollectionResponse(HZeraPage<ExampleEntity> examples) {

        var pagination = Pagination
                .builder()
                .requestedPage(examples.getNumber())
                .requestedSize(examples.getSize())
                .retrievedResults(examples.getNumberOfElements())
                .totalResults(examples.getTotalElements())
                .nextPage(HZeraPageResponseUtil.buildNextPage(examples))
                .previousPage(HZeraPageResponseUtil.buildPreviousPage(examples))
                .build();

        var exampleResourceCollection = getExampleResourceCollection(examples);
        return ExampleResourceCollectionResponse
                .builder()
                .data(exampleResourceCollection)
                .pagination(pagination)
                .build();
    }

    public ExampleResourceCollection getExampleResourceCollection(HZeraPage<ExampleEntity> examples) {
        return ExampleResourceCollection.builder()
                .examples(listExampleEntityToResource(examples.getContent()))
                .build();
    }

    public ExampleResourceResponse toResponse(ExampleEntity entity) {
        return ExampleResourceResponse.builder().data(INSTANCE.to(entity)).build();
    }

    public abstract List<ExampleResource> listExampleEntityToResource(List<ExampleEntity> list);
}
