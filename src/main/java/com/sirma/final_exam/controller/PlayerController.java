package com.sirma.final_exam.controller;

import com.sirma.final_exam.model.Player;
import com.sirma.final_exam.model.Team;
import com.sirma.final_exam.service.PlayerServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerServiceHandler playerServicehandler;

    @Autowired
    public PlayerController (PlayerServiceHandler playerServicehandler){
        this.playerServicehandler = playerServicehandler;
    }
    @PutMapping("/update/{id}")
    public Player updatePlayer(@PathVariable Integer id, @RequestBody Player player){
        return playerServicehandler.modifyPlayer(id,player);
    }
    @GetMapping
    public List<Player> showPlayers() {
        return playerServicehandler.getAllPlayers();
    }
    @PostMapping("/create")
    public Player createPlayer(@RequestBody Player player) {
        return playerServicehandler.createPlayer(player.getTeamNumber(), player.getPosition(), player.getFullName(), player.getTeam());
    }
}
