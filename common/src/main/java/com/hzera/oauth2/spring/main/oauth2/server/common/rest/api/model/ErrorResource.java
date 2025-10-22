package com.hzera.oauth2.spring.main.oauth2.server.common.rest.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResource {
    private String code;
    private String description;
    private List<String> details;
}
