package com.ssa.ironyard.fitness.dao;

import java.util.List;

import com.ssa.ironyard.fitness.model.WeeklyScore;

public interface WeeklyScoreDAO extends DAO<WeeklyScore> {

    List<WeeklyScore> readByUserId(Integer id);

}
