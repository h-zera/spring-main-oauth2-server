package com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "oauth2_consent")
@IdClass(Oauth2ConsentId.class)
public class Oauth2Consent {
    @Id
    @Column(name = "client_id")
    private UUID clientId;

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "granted_scopes", nullable = false)
    @Type(value = ListArrayType.class)
    private List<String> grantedScopes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
