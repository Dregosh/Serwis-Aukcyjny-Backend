package com.sda.serwisaukcyjnybackend.domain.observation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {
    boolean existsByAuction_IdAndAndUser_Id(Long auctionId, Long userId);
    Collection<Observation> findAllByUser_IdAndAndAuction_IdIn(Long userId, Collection<Long> auctionIds);
}
