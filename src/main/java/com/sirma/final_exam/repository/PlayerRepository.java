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
