package com.sda.serwisaukcyjnybackend.domain.observation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {
    boolean existsByAuction_IdAndAndUser_Id(Long auctionId, Long userId);

    @Query("select o from Observation o " +
            "where o.user.id = :userId " +
            "and o.auction.id = :auctionId")
    Observation findByUserAndAuction(@Param("userId") Long userId,
                                     @Param("auctionId") Long auctionId);
}
