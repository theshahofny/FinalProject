package com.ssa.ironyard.fitness.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.ssa.ironyard.fitness.model.DateHolder;
import com.ssa.ironyard.fitness.model.WorkoutHistory;

public interface WorkoutHistoryDAO extends DAO<WorkoutHistory> {

    List<WorkoutHistory> readByUserId(Integer id);

    List<WorkoutHistory> readByUserIdDate(Integer id, LocalDate date);

    List<DateHolder> GetDateAndCategory(Integer id, LocalDate date1, LocalDate date2);

    Map<String, BigDecimal> calculateStatsbyUser(Integer id);

}
