package com.hzera.oauth2.spring.main.oauth2.server.common.rest.api.model;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ApiCollectionResponse<T> {
    private List<T> data;
    private HZeraPageResponse pagination;
}
