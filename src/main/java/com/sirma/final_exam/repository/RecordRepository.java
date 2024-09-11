package com.sirma.final_exam.repository;
import com.sirma.final_exam.model.MatchRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RecordRepository extends JpaRepository<MatchRecord, Integer> {

    List<MatchRecord> findByMatchId(Integer matchId);

    @Query("SELECT mr1.player.id, mr2.player.id, " +
            "(LEAST(COALESCE(mr1.toMinutes, 90), COALESCE(mr2.toMinutes, 90)) - GREATEST(mr1.fromMinutes, mr2.fromMinutes)) as timePlayedTogether " +
            "FROM MatchRecord mr1 JOIN MatchRecord mr2 ON mr1.match = mr2.match " +
            "WHERE mr1.match.id = ?1 AND mr1.player.id > mr2.player.id")
    List<Object[]> findPlayerPairsWithTimePlayedTogether(Integer matchId);

    @Query("SELECT m.id FROM Match m")
    List<Integer> findAllMathIds();


}
