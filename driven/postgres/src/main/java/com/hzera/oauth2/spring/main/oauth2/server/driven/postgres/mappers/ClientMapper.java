package com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.mappers;

import com.hzera.oauth2.spring.main.oauth2.server.domain.entity.client.GrantTypeEnum;
import com.hzera.oauth2.spring.main.oauth2.server.domain.entity.client.RegisteredClientEntity;
import com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.client.GrantTypeMOEnum;
import com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.client.RegisteredClientMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(source = "clientSecretHash", target = "clientSecret")
    RegisteredClientEntity toEntity(RegisteredClientMO model);

    RegisteredClientMO toModel(RegisteredClientEntity entity);

    default Optional<RegisteredClientEntity> toOptionalEntity(Optional<RegisteredClientMO> model) {
        return model.map(this::toEntity);
    }

    default GrantTypeMOEnum toGrantTypeMOEnum(GrantTypeEnum grantType) {
        return GrantTypeMOEnum.valueOf(grantType.getValue());
    }

    default GrantTypeEnum toGrantTypeEnum(GrantTypeMOEnum grantTypeMO) {
        return GrantTypeEnum.valueOf(grantTypeMO.getValue().toUpperCase());
    }
}
