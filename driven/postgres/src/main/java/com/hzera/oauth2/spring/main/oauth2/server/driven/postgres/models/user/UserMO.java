package com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.user;

import com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.sso.UserIdentityMO;
import com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.token.RefreshTokenMO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserMO {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "unique_username", unique = true, nullable = false)
    private String uniqueUsername;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "email_confirmed_at")
    private OffsetDateTime emailConfirmedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "last_sign_in_at")
    private OffsetDateTime lastSignInAt;

    @Column(name = "banned_until")
    private OffsetDateTime bannedUntil;

    @Column(name = "is_sso_user", nullable = false)
    private Boolean isSsoUser;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserPhoneMO> phones;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserIdentityMO> addresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RefreshTokenMO> refreshTokens;
}
