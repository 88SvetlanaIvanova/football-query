package com.sirma.final_exam.repository;

import com.sirma.final_exam.model.Match;
import com.sirma.final_exam.model.Player;
import com.sirma.final_exam.model.Team;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository <Player, Integer> {

    List<Player> findByMatchRecordsMatchId(Integer matchId);


}
/*
The PlayerRepository interface extends JpaRepository, which is a part of the Spring Data JPA framework. 
This interface provides CRUD (Create, Read, Update, Delete) operations for entities.
It is annotated with @Repository to indicate that it is a Spring-managed repository component.

findByMatchRecordsMatchId(Integer matchId): Declares a custom query method to find a list of players based on a specific match ID.
This method leverages Spring Data JPA's query derivation mechanism to generate the query based on the method name.
*/
