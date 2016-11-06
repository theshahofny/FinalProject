package com.ssa.ironyard.fitness.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ssa.ironyard.fitness.dao.WorkoutHistoryDAO;
import com.ssa.ironyard.fitness.dao.WorkoutHistoryDAOImpl;
import com.ssa.ironyard.fitness.model.DateHolder;
import com.ssa.ironyard.fitness.model.WorkoutHistory;

@Component
public class FitnessHistoryServiceImpl
{

    WorkoutHistoryDAO daoHistory;

    @Autowired
    public FitnessHistoryServiceImpl(WorkoutHistoryDAOImpl daoHistory)
    {
        this.daoHistory = daoHistory;
    }

    @Transactional
    public List<WorkoutHistory> readAll(Integer id)
    {
        return daoHistory.readByUserId(id);
    }

    @Transactional
    public List<DateHolder> populateCalender(Integer id, LocalDate date1, LocalDate date2)
    {
        return daoHistory.GetDateAndCategory(id, date1, date2);
    }

    @Transactional
    public WorkoutHistory insertHistory(WorkoutHistory history)
    {
        return daoHistory.insert(history);
    }

    @Transactional
    public List<WorkoutHistory> readWorkoutHistoryDetail(Integer id, LocalDate date)
    {
        return daoHistory.readByUserIdDate(id, date);
    }

    @Transactional
    public List<WorkoutHistory> insertHistory(List<WorkoutHistory> userHistories)
    {
        List<WorkoutHistory> list = new ArrayList<>();

        for (WorkoutHistory h : userHistories)
            list.add(daoHistory.insert(h));
        return list;
    }

    @Transactional
    public Boolean deleteHistory(WorkoutHistory history)
    {
        return daoHistory.delete(history.getId());
    }

    public Map<String, BigDecimal> getStats(Integer id)
    {
        return daoHistory.calculateStatsbyUser(id);
    }
}
