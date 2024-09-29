package com.sirma.final_exam.service;

import com.sirma.final_exam.model.Team;
import com.sirma.final_exam.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;


@Service
public class TeamServiceHandler implements TeamService {
    @Autowired
    private  TeamRepository teamRepository;
    @Autowired
    public TeamServiceHandler(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
    private static final Logger LOGGER = Logger.getLogger(TeamServiceHandler.class.getName());
    private static final Pattern VALID_PATTERN = Pattern.compile("^[\\p{L} !@#$%^&*()_+={}\\[\\]|\\:;\"'<>,.?/-]*$");

    @Override
    public void saveTeam(Team team) {
        teamRepository.save(team);
    }

    @Override
    public Team findTeamById(Integer id) {
        return teamRepository.findById(id).orElse(null);
    }

    @Override
    public Team modifyTeam(Integer id, Team teamUpdate) {

            Team teamX = findTeamById(id);
            if (teamX != null) {
                teamX.setId(teamUpdate.getId());
                teamX.setName(teamUpdate.getName());
                teamX.setManagerFullName(teamUpdate.getManagerFullName());
                teamX.setGroup(teamUpdate.getGroup());
                teamRepository.save(teamX);
                return teamX;
            }
            return null;
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public void deleteTeam(Team team) {
    teamRepository.delete(team);
    }

    @Override
    public void deleteTeamById(Integer id) {
        teamRepository.findById(id).ifPresent(team -> teamRepository.delete(team));
    }
}
/*
TeamServiceHandler serves as the concrete implementation of the TeamService interface, encapsulating the logic for CRUD operations 
on Team entities within the application.
teamRepository: Autowired instance of TeamRepository for handling team data operations.
LOGGER: Logger instance for logging messages and errors.
VALID_PATTERN: Regular expression pattern for validating team-related strings.
 The TeamServiceHandler class utilizes constructor injection to receive the TeamRepository dependency, ensuring that the necessary 
 repository is provided at object creation.
 Implements methods for saving, finding, modifying, retrieving all teams, and deleting teams utilizing the TeamRepository for data persistence and retrieval.
 he modifyTeam method updates an existing team entity with the attributes from the provided teamUpdate object if the team exists in the repository.
*/
