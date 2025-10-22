package com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.sso;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.dialect.PostgreSQLArrayJdbcType;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sso_provider")
public class SsoProviderMO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "client_id", nullable = false)
    public String clientId;

    @Column(name = "client_secret_hash", nullable = false)
    public String clientSecretHash;

    @Column(name = "name", nullable = false)
    public String name;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    public OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    public OffsetDateTime updatedAt;

    @Column(name = "scopes", nullable = false)
    @Type(value = ListArrayType.class)
    public List<String> scopes;

    @Column(name = "auth_url", nullable = false)
    public String authUrl;

    @Column(name = "token_url", nullable = false)
    public String tokenUrl;

    @Column(name = "user_info_url", nullable = false)
    public String userInfoUrl;

    @OneToMany(mappedBy = "ssoProvider", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserIdentityMO> userIdentities;
}
