package com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.sso;

import com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.user.UserMO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_identity")
public class UserIdentityMO {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "email", nullable = false)
    private String email;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "last_sign_in_at")
    private OffsetDateTime lastSignInAt;

    //TODO: Change to JSONB type and check how to map it
    @Column(name = "identity_data")
    private String identityData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sso_id", nullable = false)
    private SsoProviderMO ssoProvider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserMO user;
}
