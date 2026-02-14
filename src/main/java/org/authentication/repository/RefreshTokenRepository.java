package org.authentication.repository;

import org.authentication.domain.entity.RefreshToken;
import org.authentication.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Query("UPDATE T_REFRESH_TOKEN rt SET rt.isRevoked = true WHERE rt.user = :user AND rt.isRevoked = false")
    @Modifying
    void revokeAllActiveTokensByUser(User user);
}

