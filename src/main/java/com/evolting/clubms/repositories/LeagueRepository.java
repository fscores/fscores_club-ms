package com.evolting.clubms.repositories;

import com.evolting.clubms.entities.Club;
import com.evolting.clubms.entities.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Integer> {
}
