package com.sda.serwisaukcyjnybackend.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Boolean existsByDisplayName(String displayName);

    List<User> findAllByAccountTypeAndPremiumAccountExpirationBefore(AccountType accountType, LocalDate now);
}
