
package com.ssa.ironyard.fitness.controller.sandbox;

import com.ssa.ironyard.fitness.model.WorkoutLogThingy;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;

/**
 *
 * @author thurston
 */
public class WorkoutHistoryJSON 
{
    final HttpMessageConverter jackson = new MappingJackson2HttpMessageConverter();
    //@Test
    public void jsonRequest() throws Exception
    {
        final String requestBody = "{" +
                     "\"date\": \"10/13/2016\"," +
                     "\"exercises\":" +
                     "[" +
                         "{" +
                            "\"distance\": \"4\"," +
                            "\"weight\":\"\"," +
                            "\"sets\":\"\"," +
                            "\"reps\":\"\"," +
                            "\"time\": \"2\"" +
                          "}" +
                       "]" +
                "}";
        
        System.err.println(requestBody);
        MockHttpInputMessage httpInput = new MockHttpInputMessage(requestBody.getBytes(StandardCharsets.UTF_8));
        WorkoutLogThingy thingy  = (WorkoutLogThingy) this.jackson.read(WorkoutLogThingy.class, httpInput);
        assertNotNull("", thingy.getWorkout_date());
        
    }
    
    @Test
    public void tryThingy() throws Exception
    {
        final String requestBody = "{" +
                     "\"date\": \"10/13/2016\"," +
                     "\"exercises\":" +
                     "[" +
                         "{" +
                            "\"distance\": \"4\"," +
                            "\"exerciseId\": 23475," +
                            "\"weight\":\"\"," +
                            "\"sets\":\"\"," +
                            "\"reps\":\"\"," +
                            "\"time\": \"2\"" +
                          "}" +
                       "]" +
                "}";
        MockHttpInputMessage httpInput = new MockHttpInputMessage(requestBody.getBytes(StandardCharsets.UTF_8));
        JacksonJSONRequest read = (JacksonJSONRequest) this.jackson.read(JacksonJSONRequest.class, httpInput);
        assertNotNull("", read.workedOut);
        assertEquals("", LocalDate.of(2016, Month.OCTOBER, 13), read.workedOut);
        assertEquals("", 1, read.exercises.size());
        
        ExerciseItem exer = read.exercises.get(0);
        assertEquals("", new BigDecimal(4), exer.distance);
        assertEquals("", null, exer.weight);
        assertEquals("", null, exer.sets);
        assertEquals("", null, exer.reps);
        
        assertEquals("", new BigDecimal(2), exer.time);
        assertEquals("", 23475, (long) exer.exerciseId);
        
    }
    
    @Test
    public void dateFormatting()
    {
        DateTimeFormatter usFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate sample = LocalDate.parse("10/12/2016", usFormatter);
        assertEquals("year", 2016, sample.getYear());
        assertEquals("month", 10, sample.getMonthValue());
        assertEquals("day", 12, sample.getDayOfMonth());
        
        sample = LocalDate.parse("09/01/2016", usFormatter);
        assertEquals("day", 1, sample.getDayOfMonth());
        assertEquals("year", 2016, sample.getYear());
        assertEquals("month", Month.SEPTEMBER, sample.getMonth());
        
        sample = LocalDate.parse("9/1/2016", usFormatter);
        assertEquals("day", 1, sample.getDayOfMonth());
        assertEquals("year", 2016, sample.getYear());
        assertEquals("month", 9, sample.getMonthValue());
        
        
    }
}

class JacksonJSONRequest
{
    static DateTimeFormatter usFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    LocalDate workedOut;
    public List<ExerciseItem> exercises = new ArrayList();
    
    public void setDate(String date)
    {
        //DateTimeFormatter.
        this.workedOut = LocalDate.parse(date, usFormatter);
    }
    
//    public void setExercises(List exercises)
//    {
//        this.exercises = exercises;
//    }
    
    public void addExerc(ExerciseItem exercise)
    {
        this.exercises.add(exercise);
        //return this.exercises;
    }
}

class ExerciseItem
{
    BigDecimal weight, time, distance;
    Integer exerciseId, reps, sets;

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