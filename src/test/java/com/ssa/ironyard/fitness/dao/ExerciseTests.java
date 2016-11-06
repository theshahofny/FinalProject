package com.ssa.ironyard.fitness.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.ssa.ironyard.fitness.dao.ExerciseDAOImpl;
import com.ssa.ironyard.fitness.dao.WorkoutHistoryDAOImpl;
import com.ssa.ironyard.fitness.model.Exercise;
import com.ssa.ironyard.fitness.model.Exercise.Category;

public class ExerciseTests {

    static String URL = "jdbc:mysql://localhost/fitness?" + "user=root&password=root" + "&useServerPrepStmt=true";

    ExerciseDAOImpl exerciseDAO;
    WorkoutHistoryDAOImpl workoutHistoryDAO;

    @Before
    public void setup() throws SQLException {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(URL);
        exerciseDAO = new ExerciseDAOImpl(mysqlDataSource);
        workoutHistoryDAO = new WorkoutHistoryDAOImpl(mysqlDataSource);

        workoutHistoryDAO.clear();
        exerciseDAO.clear();
    }

    @Test
    public void exerciseInsert() {
        Exercise e = new Exercise();
        e.setExercise_name("Push-ups");
        e.setCategory(Category.ARMS);
        e.setImage("http:image.com");
        e.setInstructions("Push up and then push down");
        e.setMuscles("Arms arms arms");
        e = exerciseDAO.insert(e);

        assertTrue(e.deeplyEquals(exerciseDAO.read(e.getId())));
    }

    @Test
    public void exerciseReadAll() {
        Exercise e = new Exercise();
        e.setExercise_name("Push-ups");
        e.setCategory(Category.ARMS);
        e.setImage("http:image.com");
        e.setInstructions("Push up and then push down");
        e.setMuscles("Arms arms arms");
        e = exerciseDAO.insert(e);

        Exercise e2 = new Exercise();
        e2.setExercise_name("Push-ups");
        e2.setCategory(Category.ARMS);
        e2.setImage("http:image.com");
        e2.setInstructions("Push up and then push down");
        e2.setMuscles("Arms arms arms");
        e2 = exerciseDAO.insert(e);

        assertEquals(2, exerciseDAO.readAll().size());
    }

}
