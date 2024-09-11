package com.sirma.final_exam.service;

import com.sirma.final_exam.util.DataUtils;
import com.sirma.final_exam.model.Match;
import com.sirma.final_exam.model.MatchRecord;
import com.sirma.final_exam.model.Player;
import com.sirma.final_exam.model.Team;
import com.sirma.final_exam.repository.MatchRepository;
import com.sirma.final_exam.repository.PlayerRepository;
import com.sirma.final_exam.repository.RecordRepository;
import com.sirma.final_exam.repository.TeamRepository;
import com.sirma.final_exam.util.CsvParser;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import org.slf4j.Logger;


@Service
public class DataLoader {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final RecordRepository recordRepository;
    private final Validator validator;
    
    static final Logger logger = org.slf4j.LoggerFactory.getLogger(DataLoader.class);

    public DataLoader(PlayerRepository playerRepository, TeamRepository teamRepository,
                      MatchRepository matchRepository, RecordRepository recordRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
        this.recordRepository = recordRepository;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /* methods, that load information from .csv and save to the repositories.
         These methods handle loading and processing data from CSV files related to
         players, teams, matches, and records in the application.
         Each method performs specific tasks related to the respective data entities
         and ensures data integrity and consistency.
     */
    public void loadPlayersCsv(String filePath) throws IOException {
        List<String[]> records = CsvParser.loadData(filePath);
        Map<Integer, Set<Integer>> teamNumberMap = new HashMap<>();
        for (String[] record : records) {
            String position = record[2].trim();
            String fullName = record[3].trim();

            if (DataUtils.isFaultyString(position)) {
                logger.error("Invalid characters in players position information: {}", position);
                continue;
            }
            if (DataUtils.isFaultyString(fullName)) {
                logger.error("Invalid characters in players full name: {}", fullName);
                continue;
            }


            int teamNumber = Integer.parseInt(record[1].trim());
            int teamId = Integer.parseInt(record[4].trim());

            if (isFaultyTeamNumber(teamNumber)) continue;


            if (isDuplicateReamNumber(teamNumberMap, teamId, teamNumber)) {
                logger.warn("Duplicate team number for team ID {}: {}", teamId, teamNumber);
                continue;
            }

            Team team = teamRepository.findById(teamId).orElse(null);
            if (team == null) {
                logger.error("Team was not found for ID: {}", teamId);
                continue;
            }

            Player player = createPlayer(teamNumber, position, fullName, team);
            validateAndSavePlayer(player);
        }
    }

    public void loadTeamsCsv(String filePath) throws IOException {
        List<String[]> records = CsvParser.loadData(filePath);
        for (String[] record : records) {
            processLineTeams(record);
        }
    }

    public void loadMatchesCsv(String filePath) throws IOException {
        List<String[]> records = CsvParser.loadData(filePath);

        for (String[] record : records) {
            if (isValidRecordLength(record, "Invalid data in matches data: {}")) {
                try {
                    int aTeamId = Integer.parseInt(record[1].trim());
                    int bTeamId = Integer.parseInt(record[2].trim());
                    LocalDate date = DataUtils.parseDate(record[3].trim());
                    String score = record[4].trim();

                    if (isValidTeamId(aTeamId) && isValidTeamId(bTeamId) && isValidScore(score)) {
                        Match match = new Match(
                                aTeamId, bTeamId, date, score
                        );
                        matchRepository.save(match);
                    } else {
                        logger.error("Invalid team ID or score format for match: {}", Arrays.toString(record));
                    }
                } catch (NumberFormatException e) {
                    logger.error("Invalid number or date format for match : {}", e.getMessage());
                }
            } else {
                logger.warn("Invalid data in matches data: {}", Arrays.toString(record));
            }

        }
    }

    public void loadRecordsCsv(String FilePath) throws IOException {
        List<String[]> records = CsvParser.loadData(FilePath);
        for (String[] record : records) {
            if (isValidRecordLength(record, "Missing data in Records: {}")) continue;
            try {
                int playerId = Integer.parseInt(record[1].trim());
                int matchId = Integer.parseInt(record[2].trim());
                int fromMin = Integer.parseInt(record[3].trim());
                String toMinStr = record[4].trim();
                Integer toMin = toMinStr.isEmpty() || toMinStr.equalsIgnoreCase("NULL") ? null : Integer.parseInt(toMinStr);
                if (playerRepository.existsById(playerId) && matchRepository.existsById(matchId)) {
                    Player player = playerRepository.findById(playerId).orElseThrow(() -> new EntityNotFoundException("Player not found: " + playerId));
                    Match match = matchRepository.findById(matchId).orElseThrow(() -> new EntityNotFoundException("Match not found: " + matchId));
                    MatchRecord matchrecord = new MatchRecord(player, match, fromMin, toMin);
                    recordRepository.save(matchrecord);
                } else {
                    logger.warn("The player or the match is not found");
                    continue;
                }
            } catch (NumberFormatException e) {
                logger.error("Invalid number format for records : {}", e.getMessage());
            }
        }
    }

    // Validation, creation  helper methods. These methods handle various validation, processing,
    // and data creation tasks related to teams, players, and records in the application.

    private boolean isValidTeamId(int teamId) {
        // In teams table there are 24 listed.
        return teamId > 0 && teamId < 24;
    }

    private boolean isValidScore(String score) {
        return score.matches("\\d+-\\d+");
    }

    private static boolean isValidRecordLength(String[] record, String s) {
        if (record.length < 5) {
            logger.warn(s, Arrays.toString(record));
            return true;
        }
        return false;
    }


    private void processLineTeams(String[] val) {
        if (val.length < 4) {
            logger.warn("Missing data in Team's : {}", Arrays.toString(val));
            return;
        }
        if (!DataUtils.areValidString(val)) {
            logger.warn("Invalid characters in Teams' data: {}", Arrays.toString(val));
            return;
        }
        String name = val[1].trim();
        String managerFullName = val[2].trim();
        String group = val[3].trim();
        Team team = new Team(
                name, managerFullName, group
        );
        teamRepository.save(team);
    }

    private void validateAndSavePlayer(Player player) {
        Set<ConstraintViolation<Player>> wrongRecords = validator.validate(player);
        if (wrongRecords.isEmpty()) {
            playerRepository.save(player);
        } else {
            wrongRecords.forEach(wrongRecord ->
                    logger.error("Wrong data provided for player: {}", wrongRecord.getMessage()));
        }
    }

    public Player createPlayer(int teamNumber, String position, String fullName, Team team) {
        return new Player(teamNumber, position, fullName, team);
    }

    public boolean isFaultyTeamNumber(int teamNumber) {
        if (teamNumber < 1 || teamNumber > 26) {
            return false;
        }
        logger.error("Invalid team number: {}", teamNumber);
        return true;
    }

    static boolean isDuplicateReamNumber(Map<Integer, Set<Integer>> teamNumberMap, int teamId, int teamNumber) {

        teamNumberMap.putIfAbsent(teamId, new HashSet<>());
        Set<Integer> usedNumbers = teamNumberMap.get(teamId);
        return !usedNumbers.add(teamNumber);
    }


}
