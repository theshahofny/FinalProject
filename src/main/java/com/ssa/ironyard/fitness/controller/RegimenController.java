package com.ssa.ironyard.fitness.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssa.ironyard.fitness.model.Account;
import com.ssa.ironyard.fitness.model.Exercise;
import com.ssa.ironyard.fitness.model.Regimen;
import com.ssa.ironyard.fitness.model.WorkoutRegimen;
import com.ssa.ironyard.fitness.services.FitnessRegimenServiceImpl;

@RestController
@RequestMapping(value = "/fitness/home")
public class RegimenController
{

    Logger LOGGER = LogManager.getLogger(RegimenController.class);

    final FitnessRegimenServiceImpl regimenService;

    @Autowired
    public RegimenController(FitnessRegimenServiceImpl s)
    {
        this.regimenService = s;
    }

    @RequestMapping(produces = "application/json", value = "/{id}/regimen", method = RequestMethod.POST)
    public ResponseEntity<Map<String, List<Regimen>>> createRegimen(@PathVariable Integer id,
            @RequestBody RegimenJSONRequest request)
    {
        WorkoutRegimen reg = regimenService.readAll(id);

        if (reg != null)
            for (Regimen r : reg.getExercises())
                regimenService.deleteRegimen(r.getId());

        Map<String, List<Regimen>> map = new HashMap<>();
        List<Regimen> regimens = new ArrayList<>();

        Account account = new Account(id);

        for (RegimenItem r : request.regimens)
        {
            Regimen r1 = new Regimen();
            r1.setAccount(account);
            r1.setDay(Regimen.DAY.valueOf(r.day));
            r1.setDistance(r.distance);
            r1.setExercise(new Exercise(r.exerciseId));
            r1.setReps(r.reps);
            r1.setSets(r.sets);
            r1.setTime(r.time);
            r1.setWeight(r.weight);

            regimens.add(r1);
        }
        regimens = regimenService.insertRegimens(regimens);

        if (regimens == null)
            map.put("error", regimens);
        else
            map.put("success", regimens);

        return ResponseEntity.ok().body(map);
    }

    @RequestMapping(produces = "application/json", value = "/{id}/regimen", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, List<Regimen>>> updateRegimen(@PathVariable Integer id,
            @RequestBody RegimenJSONRequest request)
    {
        Map<String, List<Regimen>> map = new HashMap<>();
        List<Regimen> regimens = new ArrayList<>();

        Account account = new Account(id);

        for (RegimenItem r : request.regimens)
        {
            Regimen r1 = new Regimen();
            r1.setAccount(account);
            r1.setDay(Regimen.DAY.valueOf(r.day));
            r1.setDistance(r.distance);
            r1.setExercise(new Exercise(r.exerciseId));
            r1.setReps(r.reps);
            r1.setSets(r.sets);
            r1.setTime(r.time);
            r1.setWeight(r.weight);

            regimens.add(r1);
        }
        regimens = regimenService.updateRegimens(regimens);

        if (regimens == null)
            map.put("error", regimens);
        else
            map.put("success", regimens);

        return ResponseEntity.ok().body(map);
    }

    @RequestMapping(produces = "application/json", value = "/{id}/regimen", method = RequestMethod.GET)
    public ResponseEntity<Map<String, WorkoutRegimen>> getRegimen(@PathVariable Integer id)
    {
        Map<String, WorkoutRegimen> map = new HashMap<>();

        WorkoutRegimen regimen = regimenService.readAll(id);

        if (regimen == null)
            map.put("error", regimen);
        else
            map.put("success", regimen);

        return ResponseEntity.ok().body(map);
    }

    @RequestMapping(produces = "application/json", value = "/{id}/regimen", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, Boolean>> deleteRegimen(@PathVariable Integer id)
    {
        Boolean b = false;
        ;
        Map<String, Boolean> map = new HashMap<>();

        if (regimenService.readAll(id) != null)
            b = regimenService.deleteRegimen(id);

        if (b == false)
            map.put("error", b);
        else
            map.put("success", b);

        return ResponseEntity.ok().body(map);

    }
}

/*
 * ************************************************************************************************* JSON REQUEST
 * MAPPERS
 ****************************************************************************************************
 */
class RegimenJSONRequest
{
    public final List<RegimenItem> regimens = new ArrayList<>();

    public void addRegimens(RegimenItem regimen)
    {
        this.regimens.add(regimen);
    }
}

class RegimenItem
{
    String day;
    Integer sets, reps, exerciseId;
    BigDecimal weight, time, distance;

    public void setDay(String day)
    {
        this.day = day;
    }

    public void setWeight(BigDecimal weight)
    {
        this.weight = weight;
    }

    public void setDistance(BigDecimal distance)
    {
        this.distance = distance;
    }

    public void setTime(BigDecimal time)
    {
        this.time = time;
    }

    public void setExerciseId(Integer exerciseId)
    {
        this.exerciseId = exerciseId;
    }

    public void setReps(Integer reps)
    {
        this.reps = reps;
    }

    public void setSets(Integer sets)
    {
        this.sets = sets;
    }
}
