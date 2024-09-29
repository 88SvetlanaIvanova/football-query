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
} /* The PlayerController class manages HTTP requests related to Player entities, providing endpoints for updating, retrieving, and creating player information.
    The controller class follows the RESTful design pattern, handling CRUD operations for Player entities, and delegates business logic to the PlayerServiceHandler.
    The class is annotated with @RestController to indicate it as a controller for handling RESTful requests. The @RequestMapping("/players") 
    annotation defines the base URI for the controller. 
    The PlayerServiceHandler dependency is injected into the controller through the constructor using the @Autowired annotation. 
    */
    
    

