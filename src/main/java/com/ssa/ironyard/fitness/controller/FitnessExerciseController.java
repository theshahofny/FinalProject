package com.ssa.ironyard.fitness.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssa.ironyard.fitness.model.Exercise;
import com.ssa.ironyard.fitness.services.FitnessExerciseServiceImpl;

@RestController
@RequestMapping(value = "/fitness/home")
public class FitnessExerciseController
{

    Logger LOGGER = LogManager.getLogger(FitnessExerciseController.class);

    final FitnessExerciseServiceImpl service;

    @Autowired
    public FitnessExerciseController(FitnessExerciseServiceImpl s)
    {
        this.service = s;
    }

    @RequestMapping(produces = "application/json", value = "/AllExercises", method = RequestMethod.GET)
    public ResponseEntity<List<Exercise>> getExerciseList()
    {
        ResponseEntity.status(HttpStatus.CREATED);
        List<Exercise> list = service.readAllExercises();
        LOGGER.info("AllExcersises Call starts..." + "\n"+ list);
      
        return ResponseEntity.ok().header("Fitness Exercises", "Exercise").body(list);

    }
    
    @RequestMapping(produces = "application/json", value = "/exercises/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Exercise>> getExercise(@PathVariable Integer id)
    {
        ResponseEntity.status(HttpStatus.CREATED);
        HashMap<String, List<Exercise>> map = new HashMap<String, List<Exercise>>();
        List<Exercise> list = new ArrayList<>();
        list.add(service.readExercise(id));

        if (list.size() == 0)
            map.put("error", list);
        else
            map.put("success", list);

        return ResponseEntity.ok().header("Fitness Exercises", "Exercise").body(list);

    }

}
