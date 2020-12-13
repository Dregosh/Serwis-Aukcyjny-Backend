package com.sda.serwisaukcyjnybackend.domain.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    @EntityGraph("verification-with-user")
    Optional<VerificationCode> findByCode(String code);
}
