package com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.client;

import io.hypersistence.utils.hibernate.type.array.EnumArrayType;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import io.hypersistence.utils.hibernate.type.array.internal.AbstractArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Parameter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "registered_client")
public class RegisteredClientMO {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "client_id", nullable = false, unique = true)
    private String clientId;

    @Column(name = "client_secret_hash", nullable = false)
    private String clientSecretHash;

    @Column(name = "name", nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "access_validity", nullable = false)
    private Integer accessValidity;

    @Column(name = "refresh_validity", nullable = false)
    private Integer refreshValidity;

    @Column(name = "scopes", nullable = false)
    @Type(value = ListArrayType.class)
    private List<String> scopes;

    @Column(name = "post_logout_uris")
    @Type(value = ListArrayType.class)
    private List<String> postLogoutUris;

    @Column(name = "redirect_uris")
    @Type(value = ListArrayType.class)
    private List<String> redirectUris;

    @Column(name = "grant_types", columnDefinition = "grant_type[]")
    @Type(
            value = EnumArrayType.class,
            parameters = @Parameter(
                    name = AbstractArrayType.SQL_ARRAY_TYPE,
                    value = "grant_type"
            )
    )
    private GrantTypeMOEnum[] grantTypes;

    @Column(name = "auth_methods", columnDefinition = "client_auth_method[]")
    @Type(
            value = EnumArrayType.class,
            parameters = @Parameter(
                    name = AbstractArrayType.SQL_ARRAY_TYPE,
                    value = "client_auth_method"
            )
    )
    private ClientAuthMethodsMOEnum[] authMethods;

    @Column(name = "is_first_party", nullable = false)
    private Boolean isFirstParty;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private RegisteredClientApplicationMO application;
}
