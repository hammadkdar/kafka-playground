package com.karamatics.audit.audit_service.repository;

import com.karamatics.audit.audit_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.failedAttempts = :failedAttempts WHERE u.username = :username")
    void updateFailedAttempts(@Param("username") String username, @Param("failedAttempts") Integer failedAttempts);

    @Modifying
    @Query("UPDATE User u SET u.accountNonLocked = :locked, u.lockTime = :lockTime WHERE u.username = :username")
    void lockUser(@Param("username") String username, @Param("locked") Boolean locked, @Param("lockTime") java.time.LocalDateTime lockTime);
}
