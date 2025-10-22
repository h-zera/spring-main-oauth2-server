package com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.token;

import com.hzera.oauth2.spring.main.oauth2.server.driven.postgres.models.user.UserMO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_token")
public class RefreshTokenMO {
    @Id
    @Column(name = "token_hash", nullable = false)
    private String tokenHash;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private OffsetDateTime expiresAt;

    @Column(name = "is_revoked", nullable = false)
    private Boolean isRevoked;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserMO user;
}
