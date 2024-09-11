package com.sirma.final_exam.controller;

import com.sirma.final_exam.model.Match;
import com.sirma.final_exam.service.MatchesServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchesController {
    private final MatchesServiceHandler matchesServicehandler;

    @Autowired
    public MatchesController(MatchesServiceHandler matchesServicehandler){
        this.matchesServicehandler = matchesServicehandler;
    }
    @PutMapping("/update/{id}")
    public Match updateMatch(@PathVariable Integer id, @RequestBody Match match){
        return matchesServicehandler.modifyMatch(id,match);
    }
    @GetMapping
    public List<Match> showMatches() {
        return matchesServicehandler.getAllMatches();
    }
}
