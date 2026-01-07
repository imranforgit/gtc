package com.wcl.gtc.controller;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wcl.gtc.entities.TrainingProgram;
import com.wcl.gtc.programservices.TrainingProgramService;




@RestController
@RequestMapping("/api/programs")
public class TrainingProgramController {

    private final TrainingProgramService programService;

    public TrainingProgramController(TrainingProgramService programService) {
        this.programService = programService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<TrainingProgram> createProgram(
            @RequestBody TrainingProgram program) {
        return ResponseEntity.ok(programService.createProgram(program));
    }

    // GET BY NAME
    @GetMapping("/by-name")
    public ResponseEntity<TrainingProgram> getByName(
            @RequestParam String name) {
        return ResponseEntity.ok(programService.getProgramByName(name));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<TrainingProgram>> getAll() {
        return ResponseEntity.ok(programService.getAllPrograms());
    }

}
