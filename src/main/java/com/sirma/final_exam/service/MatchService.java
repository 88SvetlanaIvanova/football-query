package com.sirma.final_exam.service;

import com.sirma.final_exam.model.Match;
import java.util.List;


public interface MatchService {
    void saveMatch(Match match);
    Match findMatchById(Integer id);
    Match modifyMatch(Integer id, Match updMatch);
    List<Match> getAllMatches();
    void deleteMatch(Match match);
    void deleteMatchById (Integer id);

}
