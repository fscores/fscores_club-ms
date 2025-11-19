package com.evolting.clubms.repositories;

import com.evolting.clubms.entities.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Integer>, JpaSpecificationExecutor<Club> {
    Optional<Club> findByPlayerIdsContaining(Integer playerId);
}
