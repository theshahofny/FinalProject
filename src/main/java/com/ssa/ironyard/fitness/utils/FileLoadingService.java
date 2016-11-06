package com.ssa.ironyard.fitness.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.ssa.ironyard.fitness.dao.ExerciseDAOImpl;
import com.ssa.ironyard.fitness.dao.RegimenDAOImpl;
import com.ssa.ironyard.fitness.dao.WorkoutHistoryDAOImpl;
import com.ssa.ironyard.fitness.model.Exercise;
import com.ssa.ironyard.fitness.model.Exercise.Category;
import com.ssa.ironyard.fitness.services.DataSourceConfiguration;

public class FileLoadingService {

    static String URL = "jdbc:mysql://localhost/fitness?" + "user=root&password=root" + "&useServerPrepStmt=true";
    static final Logger LOGGER = LogManager.getLogger(DataSourceConfiguration.class);

    MysqlDataSource mysqlDataSource;

    WorkoutHistoryDAOImpl workoutHistoryDAO;
    RegimenDAOImpl regimenDAO;
    ExerciseDAOImpl exerciseDAO;
    List<Exercise> exercises = new ArrayList<>();

    public DataSource datasource() {
        LOGGER.debug("YEAH Annotation based processing is working");
        mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(URL);
        exerciseDAO = new ExerciseDAOImpl(mysqlDataSource);
        regimenDAO = new RegimenDAOImpl(mysqlDataSource);
        workoutHistoryDAO = new WorkoutHistoryDAOImpl(mysqlDataSource);
        return mysqlDataSource;
    }

    public void intialize() throws URISyntaxException, IOException {
        datasource();
        workoutHistoryDAO.clear();
        regimenDAO.clear();
        exerciseDAO.clear();
        BufferedReader reader = null;

        try {
            reader = Files.newBufferedReader(
                    Paths.get("C:/Users/admin/workspace/Final-Project/src/main/resources/static/data.txt"));

            String line;

            while (null != (line = reader.readLine())) {
                List<String> exerciseList = Arrays.asList(line.split(","));
                String muscle = exerciseList.get(1).replaceAll("( )+", ",");
                List<String> muscles = Arrays.asList(muscle.split(","));
                muscle = muscles.get(2).replaceAll("_", " ");

                String instructions = exerciseList.get(6).replaceAll("( )+", " ");
                String cat;
                if (exerciseList.get(0).equals("Cardio"))
                    cat = "CA";
                else if (exerciseList.get(0).equals("Core"))
                    cat = "CO";
                else if (exerciseList.get(0).equals("Arms"))
                    cat = "AR";
                else if (exerciseList.get(0).equals("Back"))
                    cat = "BA";
                else if (exerciseList.get(0).equals("Legs"))
                    cat = "LE";
                else if (exerciseList.get(0).equals("Neck"))
                    cat = "NE";
                else if (exerciseList.get(0).equals("Shoulders"))
                    cat = "SH";
                else if (exerciseList.get(0).equals("Chest"))
                    cat = "CH";
                else
                    cat = "PL";

                Exercise e = new Exercise();
                e.setCategory(Category.getInstance(cat));
                e.setMuscles(muscle);
                e.setExercise_name(exerciseList.get(3).substring(2).trim());
                e.setImage(exerciseList.get(4));
                e.setInstructions(instructions);

                exercises.add(e);

            }
        } catch (IOException iex) {
            System.err.println(iex);
            throw iex;
        } finally {
            if (null != reader) {
                reader.close();
            }
        }
        List<Exercise> temp = exercises;
        Collections.sort(temp, (e1, e2) -> e1.getExercise_name().compareTo(e2.getExercise_name()));

        for (Exercise e : temp) {
            exerciseDAO.insert(e);

        }

    }
}
