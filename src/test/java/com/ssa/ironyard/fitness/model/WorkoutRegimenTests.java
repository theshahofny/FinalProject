package com.ssa.ironyard.fitness.model;

import com.ssa.ironyard.fitness.model.Regimen.DAY;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
/**
 *
 * @author thurston
 */
public class WorkoutRegimenTests 
{
    @Test
    public void sanity()
    {
        WorkoutRegimen workoutRegimen = new WorkoutRegimen();
        assertNull("", workoutRegimen.getAccount());
        assertFalse("", workoutRegimen.iterator().hasNext());
        for (DAY day : DAY.values())
            assertTrue("", workoutRegimen.daily(day).isEmpty());
    }
    
    @Test
    public void account()
    {
        WorkoutRegimen workoutRegimen = new WorkoutRegimen();
        Account user = new Account(Integer.SIZE);
        
        Regimen exercise = new Regimen();
        exercise.setDay(DAY.Sunday);
        exercise.setExercise(new Exercise((int) 'A'));
        exercise.setAccount(user);
        workoutRegimen.add(exercise);
        assertEquals("", user, workoutRegimen.getAccount());
        
        exercise = new Regimen();
        exercise.setDay(DAY.Sunday);
        exercise.setExercise(new Exercise((int) 'C'));
        exercise.setAccount(user);
        workoutRegimen.add(exercise);
        
        assertEquals("", user, workoutRegimen.getAccount());
    }
    
    @Test
    public void iterationOrder()
    {
        WorkoutRegimen workoutRegimen = new WorkoutRegimen();
        Account user = new Account(Integer.SIZE);
        
        Regimen sundayBench = new Regimen();
        sundayBench.setAccount(user);
        sundayBench.setExercise(new Exercise((int) 'B'));
        sundayBench.setDay(DAY.Sunday);
        workoutRegimen.add(sundayBench);
        
        Regimen wednesdaySwim = new Regimen();
        wednesdaySwim.setAccount(user);
        wednesdaySwim.setDay(DAY.Wednesday);
        wednesdaySwim.setExercise(new Exercise((int) 'S'));
        workoutRegimen.add(wednesdaySwim);
        
        Regimen mondayCurl = new Regimen();
        mondayCurl.setAccount(user);
        mondayCurl.setDay(DAY.Monday);
        mondayCurl.setExercise(new Exercise((int) 'C'));
        workoutRegimen.add(wednesdaySwim);
        
        //OK, iteration order should be by day-of-week
        List<Regimen> expected = new ArrayList<>(Arrays.asList(sundayBench, mondayCurl, wednesdaySwim));
        List<Regimen> actual = new ArrayList<>();
        for (Regimen regimen : workoutRegimen)
        {
            actual.add(regimen);
        }
        assertEquals("", expected, actual);
    }
    
    @Test
    public void dayFilter()
    {
        WorkoutRegimen regimen = new WorkoutRegimen();
        Account account = new Account(Integer.SIZE);
        
        Regimen monday = new Regimen();
        monday.setDay(DAY.Monday);
        monday.setAccount(account);
        monday.setExercise(new Exercise((int) 'J'));
        regimen.add(monday);
        
        Regimen wednesday = new Regimen();
        wednesday.setAccount(account);
        wednesday.setExercise(new Exercise((int) 'S'));
        wednesday.setDay(DAY.Wednesday);
        regimen.add(wednesday);
        
        monday = new Regimen();
        monday.setDay(DAY.Monday);
        monday.setAccount(account);
        monday.setExercise(new Exercise((int) 'S'));
        regimen.add(monday);
        
        
        List<Regimen> mondays = regimen.daily(DAY.Monday);
        assertEquals("", 2, mondays.size());
        for (Regimen mon : mondays)
        {
            assertTrue("", mon.getDay() == DAY.Monday);
        }
        assertEquals("", 1, mondays.stream().filter(ex -> ex.exercise.equals(new Exercise((int) 'J'))).count());
        assertEquals("", 1, mondays.stream().filter(ex -> ex.exercise.equals(new Exercise((int) 'S'))).count());
        
        List<Regimen> wednesdays = regimen.daily(DAY.Wednesday);
        assertEquals("", wednesdays, Arrays.asList(wednesday));
        
        assertEquals("", Arrays.asList(DAY.Monday, DAY.Wednesday), regimen.activeDays());
        assertEquals("", Arrays.asList(DAY.Sunday, DAY.Tuesday, DAY.Thursday, DAY.Friday, DAY.Saturday), 
                         regimen.restDays());
    }
}