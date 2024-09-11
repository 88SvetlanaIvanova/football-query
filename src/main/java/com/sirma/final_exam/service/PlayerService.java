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
