package com.ssa.ironyard.fitness.dao;

import com.ssa.ironyard.fitness.model.Exercise;
import java.util.List;

public interface ExerciseDAO extends DAO<Exercise>
{
    List<Exercise> readAll();
}
