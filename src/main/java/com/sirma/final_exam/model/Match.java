package com.sirma.final_exam.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name= "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="team_a_id")
    private Integer aTeamId;
    @Column(name="team_b_id")
    private Integer bTeamId;
    private LocalDate date;
    private String score;

    public Match(){

    }
    public Match( int aTeamId, int bTeamId, LocalDate date, String score) {
        this.aTeamId = aTeamId;
        this.bTeamId = bTeamId;
        this.date = date;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public Integer getATeamId() {
        return aTeamId;
    }

    public Integer getBTeamId() {
        return bTeamId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getScore() {
        return score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setATeamId(int aTeamId) {
        this.aTeamId = aTeamId;
    }

    public void setBTeamId(int bTeamId) {
        this.bTeamId = bTeamId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", aTeamId=" + aTeamId +
                ", bTeamId=" + bTeamId +
                ", date=" + date +
                ", score='" + score + '\'' +
                '}';
    }

} /* The class defines fields for the match ID, team IDs, date, and score, along with constructors and getter/setter methods.
It is annotated with @Entity to mark it as a JPA entity and @Table(name = "matches") to specify the table name in the database.
*/
