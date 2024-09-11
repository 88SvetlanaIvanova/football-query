package com.sirma.final_exam.model;

import java.util.Objects;

public class PlayerPair {
    private final int playerIdA;
    private final int playerIdB;
    private  int timePlayedTogether;


    public PlayerPair(int playerIdA, int playerIdB, int timePlayedTogether) {
        this.playerIdA = playerIdA;
        this.playerIdB = playerIdB;
        this.timePlayedTogether = timePlayedTogether;
    }
    public PlayerPair(int playerIdA, int playerIdB) {
        this.playerIdA = playerIdA;
        this.playerIdB = playerIdB;

    }

    public int getPlayerIdA() {
        return playerIdA;
    }

    public int getPlayerIdB() {
        return playerIdB;
    }

    public int getTimePlayedTogether() {
        return timePlayedTogether;
    }

    @Override
    public String toString() {
        return "PlayerPair{" +
                "playerIdA=" + playerIdA +
                ", playerIdB=" + playerIdB +
                ", timePlayedTogether=" + timePlayedTogether +
                '}' +
                "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerPair other = (PlayerPair) o;
        return (playerIdA == other.playerIdA && playerIdB == other.playerIdB) ||
                (playerIdA == other.playerIdB && playerIdB == other.playerIdA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.min(playerIdA, playerIdB), Math.max(playerIdA, playerIdB));
    }
}
