package com.wcl.gtc.programservices;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wcl.gtc.entities.TrainingProgram;
import com.wcl.gtc.repositories.TrainingProgramRepository;
@Service
public class TrainingProgramServiceImpl implements TrainingProgramService {


    private TrainingProgramRepository repository; 
    public TrainingProgramServiceImpl(TrainingProgramRepository repository) {
        this.repository = repository;
    }       

    @Override
    public TrainingProgram createProgram(TrainingProgram program) {
        if (repository.existsByName(program.getName())) {
            throw new RuntimeException("Training program already exists");
        }
        return repository.save(program);
    }

    @Override
    public TrainingProgram getProgramByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Program not found"));
    }

    @Override
    public List<TrainingProgram> getAllPrograms() {
        return repository.findAll();
    }

}
