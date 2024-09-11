package com.sirma.final_exam.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String managerFullName;

    @Column(name = "`group`", nullable = false)
    private String group;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Player> players;

    public Team(){

    }
    public Team( String name, String managerFullName, String group) {

        this.name = name;
        this.managerFullName = managerFullName;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getManagerFullName() {
        return managerFullName;
    }

    public String getGroup() {
        return group;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManagerFullName(String managerFullName) {
        this.managerFullName = managerFullName;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
