package com.sirma.final_exam.controller;
import com.sirma.final_exam.service.PlayerPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api")
public class PlayerPairController {
    @Autowired
    private PlayerPairService playerPairService;

    @GetMapping("/longest-playing-players")
    public String getLongestPlayingPlayers(){
        return playerPairService.findLongestPlayingPlayersDifferentTeamsSamePair();

    }
}
