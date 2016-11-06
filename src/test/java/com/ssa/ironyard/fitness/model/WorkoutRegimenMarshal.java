package com.ssa.ironyard.fitness.model;

import com.ssa.ironyard.fitness.model.Regimen.DAY;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
/**
 *
 * @author thurston
 */
public class WorkoutRegimenMarshal 
{
    MappingJackson2HttpMessageConverter jackson = new MappingJackson2HttpMessageConverter();
    
    @Test
    public void simulateMarshalling() throws Exception
    {
        WorkoutRegimen regimen = new WorkoutRegimen();
        Account account = new Account(Integer.SIZE);
        Regimen exercise = new Regimen();
        exercise.setAccount(account);
        exercise.setDay(DAY.Sunday);
        exercise.setExercise(new Exercise((int) 'B'));
        exercise.setId(78);
        
        regimen.add(exercise);
        MockHttpOutputMessage response = new MockHttpOutputMessage();
        this.jackson.write(regimen, MediaType.APPLICATION_JSON_UTF8, response);
        
        System.err.println(response.getBodyAsString());
        
    }
}