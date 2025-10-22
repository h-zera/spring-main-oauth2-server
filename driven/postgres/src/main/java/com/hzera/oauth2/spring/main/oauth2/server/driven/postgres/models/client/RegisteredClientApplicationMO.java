package com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.client;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "registered_client_application")
public class RegisteredClientApplicationMO {
    @Id
    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "logo_url", nullable = false)
    private String logoUrl;

    @Column(name = "policy_url", nullable = false)
    private String policyUrl;

    @Column(name = "revocation_webhook_uri", nullable = false)
    private String revocationWebhookUri;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "client_id")
    private RegisteredClientMO client;
}
