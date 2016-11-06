
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
public class RegimenControllerTest 
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
                            "\"day\": \"Monday\"," +
                            "\"weight\":\"300\"," +
                            "\"sets\":\"2\"," +
                            "\"reps\":\"5\"," +
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
        final String requestBody = "{" + "\"regimens\":" +
                     "[" +
                         "{" +
                            "\"distance\": \"4\"," +
                            "\"exerciseId\": 23475," +
                            "\"day\": \"Monday\"," +
                            "\"weight\":\"300\"," +
                            "\"sets\":\"2\"," +
                            "\"reps\":\"5\"," +
                            "\"time\": \"\"" +
                          "}, " +
                          "{" +
                          "\"distance\": \"100\"," +
                          "\"exerciseId\": 75," +
                          "\"day\": \"Saturday\"," +
                          "\"weight\":\"300\"," +
                          "\"sets\":\"2\"," +
                          "\"reps\":\"5\"," +
                          "\"time\": \"\"" +
                        "}" +
                       "]" +
                "}";
        MockHttpInputMessage httpInput = new MockHttpInputMessage(requestBody.getBytes(StandardCharsets.UTF_8));
        RegimenJSONRequest read = (RegimenJSONRequest) this.jackson.read(RegimenJSONRequest.class, httpInput);
        
       // System.err.println(requestBody);

        assertEquals("", 2, read.regimens.size());
        
        RegimenItem item = read.regimens.get(0);
        assertEquals("", "Monday", item.day);
        assertEquals("", new BigDecimal(4), item.distance);
        assertEquals("", new BigDecimal(300), item.weight);
        assertEquals("", new Integer(2), item.sets);
        assertEquals("", new Integer(5), item.reps);
        
        assertEquals("", null, item.time);
        assertEquals("", 23475, (long) item.exerciseId);
        
        item = read.regimens.get(1);
        assertEquals("", "Saturday", item.day);
        assertEquals("", new BigDecimal(100), item.distance);
        assertEquals("", new BigDecimal(300), item.weight);
        assertEquals("", new Integer(2), item.sets);
        assertEquals("", new Integer(5), item.reps);
        
        assertEquals("", null, item.time);
        assertEquals("", 75, (long) item.exerciseId);
        
    }
    
   
}

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