package com.ssa.ironyard.fitness.dao;

import com.ssa.ironyard.fitness.model.WorkoutRegimen;

public interface RegimenDAO {

    
    WorkoutRegimen readByUserId(Integer id);

}
