package com.sirma.final_exam.service;

import com.sirma.final_exam.model.Match;
import com.sirma.final_exam.model.MatchRecord;
import com.sirma.final_exam.model.PlayerPair;
import com.sirma.final_exam.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.math3.util.Combinations;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class PlayerPairService {
    private final RecordRepository matchRecordRepository;
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    public PlayerPairService(RecordRepository matchRecordRepository) {
        this.matchRecordRepository = matchRecordRepository;
    }
    public String findLongestPlayingPlayersDifferentTeamsSamePair() {

        List<Object[]> playersIdDesc = new ArrayList<>();
        List<Object[]> similarPairs = new ArrayList<>();
        List<Integer> findAllIds = matchRecordRepository.findAllMathIds();

        findAllIds.forEach(matchId -> {
            List<Object[]> playersIdDescending = matchRecordRepository.findPlayerPairsWithTimePlayedTogether(matchId);
            playersIdDesc.addAll(playersIdDescending);
        });
        for (int i = 0; i < playersIdDesc.size(); i++) {
            Object[] pair1 = playersIdDesc.get(i);
            Integer player11Id = (Integer) pair1[0];
            Integer player12Id = (Integer) pair1[1];
            int timePlayedTogether1 = (int) pair1[2];
            int totalPlayedTogether = timePlayedTogether1;

            for (int j = i + 1; j < playersIdDesc.size(); j++) {
                Object[] pair2 = playersIdDesc.get(j);
                Integer player21Id = (Integer) pair2[0];
                Integer player22Id = (Integer) pair2[1];
                int timePlayedTogether2 = (int) pair2[2];

                if ((player11Id.equals(player21Id) && player12Id.equals(player22Id)) ||
                        (player11Id.equals(player22Id) && player12Id.equals(player21Id))) {

                    totalPlayedTogether += timePlayedTogether2;
                }
            }
            Object[] combinedPair = {player11Id, player12Id, totalPlayedTogether};
            similarPairs.add(combinedPair);
        }
        return printObj(similarPairs);
    }

    public String printObj(List<Object[]> pairs) {
        pairs.sort(Comparator.comparingInt(o -> (int) ((Object[]) o)[2]).reversed());
        StringBuilder displayData = new StringBuilder();
        int count = 0;
        for (Object[] pair : pairs) {
            if (count >= 10) {
                break;
            }
            Integer player1Id = (Integer) pair[0];
            Integer player2Id = (Integer) pair[1];
            int timePlayedTogether = (int) pair[2];
            displayData
                    .append(player1Id)
                    .append(" " + player2Id)
                    .append(" : " + timePlayedTogether)
                    .append("--");
            count++;
        }
        return displayData.toString();
    }


    // These were previous attempts methods, not used in the solution
    // unused methods, that were tried to solve the problem
    public static Map<PlayerPair, Integer> groupSortPair(Map<PlayerPair, Integer> pairs) {
        if (pairs == null || pairs.isEmpty()) {
            throw new IllegalArgumentException("Input map cannot be null or empty");
        }
        Map<PlayerPair, Integer> aggregatedResult = new HashMap<>();
        for (Map.Entry<PlayerPair, Integer> entry : pairs.entrySet()) {
            PlayerPair playerPair = entry.getKey();
            int timePlayed = entry.getKey().getTimePlayedTogether();

            int totalPlayedTime = aggregatedResult.getOrDefault(playerPair, 0) + timePlayed;
            aggregatedResult.put(playerPair, totalPlayedTime);
        }
        return aggregatedResult;
    }
    public String takeTopPair(Map<PlayerPair, Integer> result) {
        if (result.isEmpty()) {
            return "No player pairs found.";
        }

        Map.Entry<PlayerPair, Integer> maxEntry = null;
        for (Map.Entry<PlayerPair, Integer> entry : result.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }

        if (maxEntry != null) {
            PlayerPair maxDurationPlayerPair = maxEntry.getKey();
            return "Player pair: " + maxDurationPlayerPair.getPlayerIdA() + " and " + maxDurationPlayerPair.getPlayerIdB() +
                    ", the time: " + maxEntry.getValue();
        }
        return "No player pairs found.";
    }
    private static String extractedPairs(Map<PlayerPair, Integer> playerPairsDuration) {
        for (Map.Entry<PlayerPair, Integer> entry : playerPairsDuration.entrySet()) {
            PlayerPair playerPair = entry.getKey();
            Integer duration = entry.getValue();

            return "Player Pair: " + playerPair + " - Duration: " + duration;
        }
        return null;
    }
    private MatchRecord getPlayerWithId(List<MatchRecord> players, int playerId) {
        for (MatchRecord player : players) {
            if (player.getPlayer().getId() == playerId) {
                return player;
            }
        }
        return null;
    }
    public String findLongestPlayingPlayersDifferentTeamsSameMatch() {
        // final result here
        Map<PlayerPair, Integer> playerPairsDuration = new HashMap<>();
        Map<Integer, List<MatchRecord>> matchRecordsByMatchId = new HashMap<>();
        // Gather unique player matches using a Set
        Set<MatchRecord> uniqueRecords = new HashSet<>(matchRecordRepository.findAll());
        Set<Integer> processedMatchIds = new HashSet<>();
        // saving combinations and time played together here
        // Map <List<Integer>, Integer>  playerPairsTime = new HashMap<>();
        uniqueRecords.forEach(record -> {
            Match match = record.getMatch();
            if (!processedMatchIds.contains(match.getId())) {
                matchRecordsByMatchId.put(match.getId(), matchRecordRepository.findByMatchId(match.getId()));
                processedMatchIds.add(match.getId());
            }

            //Creating players combinations.
            for (List<MatchRecord> matchRecords : matchRecordsByMatchId.values()) {
                int numPlayers = matchRecords.size();
                int[] playerIndices = new int[numPlayers];
                for (int i = 0; i < numPlayers; i++) {
                    playerIndices[i] = i;
                }
// constructor gets two integers: the total number of elements and the size of the combinations.
                Combinations combinations = new Combinations(numPlayers, 2);
                for (int[] pair : combinations) {
                    MatchRecord matchRecordA = matchRecords.get(pair[0]);
                    MatchRecord matchRecordB = matchRecords.get(pair[1]);

                    int playerA = matchRecordA.getPlayer().getId();
                    int playerB = matchRecordB.getPlayer().getId();

                    int timePlayedTogether = Math.min(
                            Objects.requireNonNullElse(matchRecordA.getToMinutes(), 90),
                            Objects.requireNonNullElse(matchRecordB.getToMinutes(), 90)
                    ) - Math.max(matchRecordA.getFromMinutes(), matchRecordB.getFromMinutes());

                    PlayerPair combPair = new PlayerPair(playerA, playerB, timePlayedTogether);
                    playerPairsDuration.put(combPair, timePlayedTogether);
                }
            }
        });

        return takeTopPair(groupSortPair(playerPairsDuration));
    }
    public String findLongestPlayingPlayersDifferentTeamsSameMatch1() {

        List<MatchRecord> records = new ArrayList<>(matchRecordRepository.findAll());
        Comparator<MatchRecord> matchIdComparator = Comparator.comparingInt(mr -> mr.getMatch().getId());

        List<MatchRecord> sortedRecords = records.stream()
                .sorted(matchIdComparator)
                .toList();

        List<PlayerPair> allPairs = new ArrayList<>();


        for (MatchRecord sortRecord : sortedRecords) {
            List<PlayerPair> matchPairs = calculatePlayerMinutes(sortedRecords);
            allPairs.addAll(matchPairs);
        }

        Map<PlayerPair, Integer> totalMinutesMap = new HashMap<>();

        for (PlayerPair ppm : allPairs) {
            PlayerPair pair = new PlayerPair(ppm.getPlayerIdA(), ppm.getPlayerIdB());
            totalMinutesMap.put(pair, totalMinutesMap.getOrDefault(pair, 0) + ppm.getTimePlayedTogether());
        }

        Map.Entry<PlayerPair, Integer> maxEntry = totalMinutesMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();

        if (maxEntry.getValue() != null) {
            return "Max Pair: " + maxEntry.getKey() + " with " + maxEntry.getValue() + " minutes";
        }
        return "Pairs are not found.";

    }
    private static List<PlayerPair> calculatePlayerMinutes(List<MatchRecord> matchRecords) {
        List<PlayerPair> playerMinutesList = new ArrayList<>();
        int counter = 0;

        for (int i = 0; i < matchRecords.size(); i++) {
            if (counter == 3) {
                break;
            }
            MatchRecord r1 = matchRecords.get(i);
            for (int j = i + 1; j < matchRecords.size(); j++) {
                MatchRecord r2 = matchRecords.get(j);
                int from1 = r1.getFromMinutes();
                int to1 = Objects.requireNonNullElse(r1.getToMinutes(), 90);
                int from2 = r2.getFromMinutes();
                int to2 = Objects.requireNonNullElse(r2.getToMinutes(), 90);

                int mutualMinutes = Math.min(to1, to2) - Math.max(from1, from2);
                if (mutualMinutes > 80) {
                    playerMinutesList.add(new PlayerPair(r1.getPlayer().getId(), r2.getPlayer().getId(), mutualMinutes));
                }
            }
            counter++;
        }
        return playerMinutesList;
    }


}





