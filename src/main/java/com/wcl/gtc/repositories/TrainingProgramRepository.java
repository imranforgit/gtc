package com.wcl.gtc.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wcl.gtc.entities.TrainingProgram;
@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long>     {
    Optional<TrainingProgram> findByName(String name);

    boolean existsByName(String name);
}
