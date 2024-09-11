package com.sirma.final_exam.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="players")
     //   uniqueConstraints = {@UniqueConstraint(columnNames = {"team_id", "teamNumber"},})
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private int teamNumber;
    @NotNull
    @Size(min = 2, max = 3)
    private String position;
    @NotNull
    @Size(min = 2, max = 50)
    private String fullName;



    @OneToMany(mappedBy = "player")
    @JsonIgnoreProperties("player")
    private Set<MatchRecord> matchRecords;

   public Player(){

   }
    public Player(int teamNumber, String position, String fullName, Team team) {
        this.teamNumber = teamNumber;
        this.position = position;
        this.fullName = fullName;
        this.team = team;
    }


    public int getId() {
        return id;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public String getPosition() {
        return position;
    }

    public String getFullName() {
        return fullName;
    }

    public Team getTeam() {
        return team;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setTeamId(Team team) {
        this.team = team;
    }


}
