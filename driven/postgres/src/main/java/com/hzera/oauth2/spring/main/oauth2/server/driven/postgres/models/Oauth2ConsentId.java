package com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oauth2ConsentId {
    private UUID clientId;
    private UUID userId;
}
