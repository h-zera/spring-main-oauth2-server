package com.hzera.oauth2.spring.main.oauth2.server.domain.entity.client;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredApplicationEntity {
    private String description;
    private String logoUrl;
    private String policyUrl;
    private String revocationWebhookUri;

    @NonNull
    private RegisteredClientEntity registeredClient;
}