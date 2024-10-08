package com.sirma.final_exam.repository;

import com.sirma.final_exam.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository  extends JpaRepository<Match, Integer> {


}
