package com.sirma.final_exam.controller;
import com.sirma.final_exam.model.Team;
import com.sirma.final_exam.service.TeamServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamsController {
    private final TeamServiceHandler teamServicehandler;

    @Autowired
    public TeamsController(TeamServiceHandler teamServicehandler){
        this.teamServicehandler = teamServicehandler;
    }
    @PutMapping("/update/{id}")
    public Team updateTeam(@PathVariable Integer id, @RequestBody Team team){
        return teamServicehandler.modifyTeam(id,team);
    }
    @GetMapping
    public List<Team> showPlayers() {
        return teamServicehandler.getAllTeams();
    }
}
/*The TeamsController class is responsible for handling HTTP requests related to Team entities.
It defines endpoints for updating and retrieving Team information.
The controller class follows the Spring MVC pattern by delegating business logic to the TeamServiceHandler.  */
