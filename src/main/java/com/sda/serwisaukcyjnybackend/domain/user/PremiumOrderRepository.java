package com.sda.serwisaukcyjnybackend.domain.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PremiumOrderRepository extends CrudRepository<PremiumOrder, Long> {
    @Query("select case when count(p) > 0 then true else false end " +
            "from PremiumOrder p " +
            "where p.user.id = :userId")
    boolean existsByUserId(@Param("userId") Long userId);

    Optional<PremiumOrder> findByOrderId(String orderId);

    List<PremiumOrder> findAllByOrderDateBefore(LocalDateTime time);
}
