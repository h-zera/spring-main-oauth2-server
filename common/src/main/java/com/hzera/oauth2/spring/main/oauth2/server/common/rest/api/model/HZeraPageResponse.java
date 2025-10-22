package com.hzera.oauth2.spring.main.oauth2.server.common.rest.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HZeraPageResponse {
    private int page;
    private int requestedSize;
    private int count;
    private Long total;
    private AltPage nextPage;
    private AltPage previousPage;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AltPage {
        private int page;
        private int size;
    }
}
