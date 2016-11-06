package com.ssa.ironyard.fitness.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssa.ironyard.fitness.model.Account;
import com.ssa.ironyard.fitness.model.DateHolder;
import com.ssa.ironyard.fitness.model.Exercise;
import com.ssa.ironyard.fitness.model.WorkoutHistory;
import com.ssa.ironyard.fitness.services.FitnessHistoryServiceImpl;

@RestController
@RequestMapping(value = "/fitness/home")
public class HistoryController {

	Logger LOGGER = LogManager.getLogger(HistoryController.class);
	final FitnessHistoryServiceImpl histService;

	@Autowired
	public HistoryController(FitnessHistoryServiceImpl w) {
		this.histService = w;
	}

	@RequestMapping(produces = "application/json", value = "/{id}/history", method = RequestMethod.GET)
	public ResponseEntity<Map<String, List<WorkoutHistory>>> getWorkoutHistory(@PathVariable Integer id) {
		Map<String, List<WorkoutHistory>> map = new HashMap<>();

		List<WorkoutHistory> history = histService.readAll(id);

		if (history == null)
			map.put("error", history);
		else
			map.put("success", history);

		return ResponseEntity.ok().header("Fitness", "Workout History").body(map);

	}

	@RequestMapping(produces = "application/json", value = "/{id}/calendarDetail", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<WorkoutHistory>>> getExercisedetailforDay(@RequestParam("date") String date, @PathVariable int id)	
    {
    	DateTimeFormatter usFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    	LocalDate sample = LocalDate.parse(date, usFormatter);
//    	List<String> temp = Arrays.asList(date.split("/"));
//    	
//        LocalDate sample = LocalDate.of(Integer.parseInt(temp.get(0)),Integer.parseInt(temp.get(1)),Integer.parseInt(temp.get(2)));
        
        ResponseEntity.status(HttpStatus.CREATED);
        Map<String, List<WorkoutHistory>> map = new HashMap<>();
        List<WorkoutHistory> history = histService.readWorkoutHistoryDetail(id, sample);

        if (history == null)
            map.put("error", history);
        else
            map.put("success", history);
        
        LOGGER.info("AllExcersises Call starts..." + "\n" + map);
      
        return ResponseEntity.ok().header("Fitness Exercises", "Exercise").body(map);
    }

	@RequestMapping(produces = "application/json", value = "/{id}/history", method = RequestMethod.POST)
	public ResponseEntity<Map<String, List<WorkoutHistory>>> addWorkoutsToHistory(@PathVariable Integer id,
			@RequestBody WorkoutJSONRequest request) {
		Map<String, List<WorkoutHistory>> response = new HashMap<>();
		List<WorkoutHistory> workouts = new ArrayList<>();

		Account account = new Account(id);
		LocalDate workoutDate = request.workedOut;

		for (ExerciseItem exercise : request.exercises) {
			WorkoutHistory history = new WorkoutHistory();
			try {
				history.setWorkout_date(workoutDate);
			} catch (Exception e) {
				LOGGER.trace("Workout history date is null!");
			}
			history.setAccount(account);
			history.setDistance(exercise.distance);
			history.setExercise(new Exercise(exercise.exerciseId));
			history.setReps(exercise.reps);
			history.setSets(exercise.sets);
			history.setTime(exercise.time);
			history.setWeight(exercise.weight);

			workouts.add(history);
		}

		workouts = histService.insertHistory(workouts);

		if (workouts.isEmpty())
			response.put("error", workouts);
		else
			response.put("success", workouts);

		return ResponseEntity.ok().header("Fitness", "Workout History").body(response);

	}

	@RequestMapping(produces = "application/json", value = "/{id}/calendar/", method = RequestMethod.GET)
	public ResponseEntity<List<DateHolder>> calenderFill(@RequestParam("start") String date1,
			@RequestParam("end") String date2, @PathVariable int id) {
		
		DateTimeFormatter usFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		LocalDate sample = LocalDate.parse(date1, usFormatter);
		LocalDate sample2 = LocalDate.parse(date2, usFormatter);

		ResponseEntity.status(HttpStatus.CREATED);
		List<DateHolder> calendar = histService.populateCalender(id, sample, sample2);

		if (calendar.size() == 0)
			calendar = Collections.emptyList();
		
		
		LOGGER.info("AllExcersises Call starts..." + "\n" + calendar);

		return ResponseEntity.ok().header("Fitness Exercises", "Exercise").body(calendar);
	}

}

/*
 * *****************************************************************************
 * ******************** JSON REQUEST MAPPERS
 ****************************************************************************************************
 */
class WorkoutJSONRequest {
	static final DateTimeFormatter usFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
	LocalDate workedOut;

	public final List<ExerciseItem> exercises = new ArrayList<>();

	public void setDate(String date) {
		// DateTimeFormatter.
		this.workedOut = LocalDate.parse(date, usFormatter);
	}

	// public void setExercises(List exercises)
	// {
	// this.exercises = exercises;
	// }

	public void addExerc(ExerciseItem exercise) {
		this.exercises.add(exercise);
		// return this.exercises;
	}
}

class ExerciseItem {
	BigDecimal weight, time, distance;
	Integer exerciseId, reps, sets;

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public void setTime(BigDecimal time) {
		this.time = time;
	}

	public void setExerciseId(Integer exerciseId) {
		this.exerciseId = exerciseId;
	}

	public void setReps(Integer reps) {
		this.reps = reps;
	}

	public void setSets(Integer sets) {
		this.sets = sets;
	}
}
