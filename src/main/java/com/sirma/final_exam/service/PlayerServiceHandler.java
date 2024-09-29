package com.sirma.final_exam.service;

import com.sirma.final_exam.model.Player;
import com.sirma.final_exam.model.Team;
import com.sirma.final_exam.repository.MatchRepository;
import com.sirma.final_exam.repository.PlayerRepository;
import com.sirma.final_exam.repository.TeamRepository;
import com.sirma.final_exam.util.DataUtils;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.simple.SimpleLogger;
import static com.sirma.final_exam.service.DataLoader.logger;

@Service
public class PlayerServiceHandler implements PlayerService {
    @Autowired
    private final PlayerRepository playerRepo;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    private final DataLoader dataloader;
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DataLoader.class);


    @Autowired
    public PlayerServiceHandler(PlayerRepository playerRepo, DataLoader dataloader) {
        this.playerRepo = playerRepo;
        this.dataloader = dataloader;
    }

    public Player createPlayer(int teamNumber, String position, String fullName, Team team){
       // tem number 1 - 26
        boolean allowedToCreate = true;
        if (DataUtils.isFaultyString(position)) {
           logger.error("Invalid characters in players position information: {}", position);
            allowedToCreate = false;
        }
        if (DataUtils.isFaultyString(fullName)) {
            logger.error("Invalid characters in players full name: {}", fullName);
            allowedToCreate = false;
        }

        if (dataloader.isFaultyTeamNumber(teamNumber)){
            logger.error("Invalid team number: {}", teamNumber);
            allowedToCreate = false;
        }

        if (teamNumber < 1 || teamNumber > 26) {
            logger.warn("Duplicate team number {}", teamNumber);
            allowedToCreate = false;
        }
        if( teamRepository.findById(team.getId()).orElse(null) == null){
            allowedToCreate=false;
        }


        if(allowedToCreate) return dataloader.createPlayer(teamNumber,position, fullName, team);

       return null;
    }

    @Override
    public void savePlayer(Player player) {
        playerRepo.save(player);
    }

    @Override
    public Player findPlayerById(Integer id) {
        return playerRepo.findById(id).orElse(null);
    }

    @Override
    public Player modifyPlayer(Integer id, Player playerUpdate) {
        Player playerX = findPlayerById(id);
        if (playerX != null) {
            playerX.setTeamId(playerUpdate.getTeam());
            playerX.setPosition(playerUpdate.getPosition());
            playerX.setTeamNumber(playerUpdate.getTeamNumber());
            playerX.setFullName(playerUpdate.getFullName());
            return playerRepo.save(playerX);
        }

        return null;
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepo.findAll();
    }

    @Override
    public void deletePlayer(Player player) {
        playerRepo.delete(player);
    }

    @Override
    public void deletePlById(Integer id) {
        playerRepo.deleteById(id);
    }
} /*
The PlayerServiceHandler class is a Spring service component that implements the PlayerService interface, 
providing the implementation for managing Player entities. It handles various CRUD operations 
for Player entities using the PlayerRepository for data access.
createPlayer Method: Validates input parameters for creating a new player entity, performs checks on team number validity, 
position, full name, and team existence before creating a player using the dataloader component.
*/
