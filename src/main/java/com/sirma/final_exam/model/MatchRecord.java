package com.sirma.final_exam.model;

import jakarta.persistence.*;

@Entity
@Table(name = "match_records")
public class MatchRecord {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "match_id")
    private Match match;
    private Integer fromMinutes;
    private Integer toMinutes;

    MatchRecord(){

    }

    public MatchRecord(Player player, Match match, Integer fromMinutes, Integer toMinutes) {
        this.player = player;
        this.match = match;
        this.fromMinutes = fromMinutes;
        this.toMinutes = toMinutes;
    }

    public Player getPlayer() {
        return player;
    }

    public Integer getId() {
        return id;
    }

    public Match getMatch() {
        return match;
    }

    public Integer getFromMinutes() {
        return fromMinutes;
    }

    @Override
    public String toString() {
        return "MatchRecord{" +
                "id=" + id +
                ", player=" + player +
                ", match=" + match +
                ", fromMinutes=" + fromMinutes +
                ", toMinutes=" + toMinutes +
                '}'+
                '\n';
    }

    public Integer getToMinutes() {
        return toMinutes;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setFromMinutes(Integer fromMinutes) {
        this.fromMinutes = fromMinutes;
    }

    public void setToMinutes(Integer toMinutes) {
        if(toMinutes==null){
            this.toMinutes = 90;
        }
        this.toMinutes = toMinutes;
    }

}
