package com.wcl.gtc.programservices;

import java.util.List;

import com.wcl.gtc.entities.TrainingProgram;

public interface TrainingProgramService {
 
    TrainingProgram createProgram(TrainingProgram program);

    TrainingProgram getProgramByName(String name);

    List<TrainingProgram> getAllPrograms();
}
