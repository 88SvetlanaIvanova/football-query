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
/*
The RecordRepository interface extends JpaRepository, a part of the Spring Data JPA framework, to provide data access operations for the MatchRecord entity.
It includes custom query methods annotated with @Query to define specific SQL queries for retrieving data from the database.

findByMatchId(Integer matchId): A custom query method that retrieves a list of MatchRecords based on a specific match ID.
findAllMathIds(): Annotated with @Query, this method retrieves a list of all match IDs from the Match entity.
findPlayerPairsWithTimePlayedTogether(Integer matchId): Annotated with @Query, 
this method performs a complex SQL query to find player pairs who played together in a match, calculating the time played together.
The SELECT clause specifies the columns to be retrieved from the database. Here, it selects the player IDs from two instances of 
the MatchRecord entity, denoted as mr1 and mr2.
(LEAST(...) - GREATEST(...)) as timePlayedTogether: This part calculates the time played together for the player pair. 
It subtracts the maximum start time and the minimum end time to determine the duration they played together. The result is aliased as timePlayedTogether.
FROM MatchRecord mr1 JOIN MatchRecord mr2 ON mr1.match = mr2.match: This specifies the tables involved in the query. It joins two instances of 
the MatchRecord entity, mr1 and mr2, on the match column to correlate the data between them.
WHERE mr1.match.id = ?1 AND mr1.player.id > mr2.player.id: The WHERE clause filters the results based on conditions. It ensures that both MatchRecords 
are from the same match (mr1.match.id = ?1) and that the player ID of mr1 is greater than the player ID of mr2.This condition ensures that 
the player ID of mr1 is greater than the player ID of mr2. By enforcing this comparison, the query avoids duplicating pairs of players in reverse order, thus maintaining uniqueness.
*/
