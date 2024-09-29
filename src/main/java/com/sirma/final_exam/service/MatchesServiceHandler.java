package com.sirma.final_exam.service;

import com.sirma.final_exam.model.Match;
import com.sirma.final_exam.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MatchesServiceHandler implements MatchService{
    @Autowired
    private MatchRepository matchRepository;

    public MatchesServiceHandler(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public void saveMatch(Match match) {
        matchRepository.save(match);
    }

    @Override
    public Match findMatchById(Integer id) {
        return (matchRepository.findById(id).orElse(null));
    }

    @Override
    public Match modifyMatch(Integer id, Match updMatch) {
        Match matchX = findMatchById(id);
        if (matchX != null) {
            matchX.setATeamId(updMatch.getATeamId());
            matchX.setBTeamId(updMatch.getBTeamId());
            matchX.setDate(updMatch.getDate());
            matchX.setId(updMatch.getId());
            matchX.setScore(updMatch.getScore());
            return matchRepository.save(matchX);
        }
        return null;
    }

    @Override
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public void deleteMatch(Match match) {
        matchRepository.delete(match);
    }

    @Override
    public void deleteMatchById(Integer id) {
        matchRepository.deleteById(id);
    }
}
/*
MatchesServiceHandler acts as the implementation class for the MatchService interface, handling CRUD
operations for Match entities using the MatchRepository for data access.
The MatchRepository bean is injected into the MatchesServiceHandler class using the @Autowired annotation, 
enabling data access operations on the Match entities.
*/
