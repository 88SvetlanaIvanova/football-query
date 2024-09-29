package com.sirma.final_exam.service;

import com.sirma.final_exam.model.Player;
import com.sirma.final_exam.model.Team;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PlayerService {
    Player createPlayer(int teamNumber, String position, String fullName, Team team);
    void savePlayer(Player player);
    Player findPlayerById(Integer id);
    Player modifyPlayer(Integer id, Player player);
    List<Player> getAllPlayers();
    void deletePlayer(Player player);
    void deletePlById (Integer id);
}
/*
The PlayerService interface abstracts the business logic associated with Player entities, promoting modularity and 
separation of concerns within the application architecture.
It defines the contract for managing player data, offering a structured API for interacting with Player-related functionalities.
*/
