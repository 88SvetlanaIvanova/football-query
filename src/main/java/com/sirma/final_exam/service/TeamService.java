package com.sirma.final_exam.service;
import com.sirma.final_exam.model.Team;

import java.util.List;

public interface TeamService {
    void saveTeam(Team team);
    Team findTeamById(Integer id);
    Team modifyTeam(Integer id, Team team);
    List<Team> getAllTeams();
    void deleteTeam(Team team);
    void deleteTeamById (Integer id);

}
