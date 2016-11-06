package com.ssa.ironyard.fitness.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.ssa.ironyard.fitness.model.CategoryHolder;
import com.ssa.ironyard.fitness.model.DateHolder;
import com.ssa.ironyard.fitness.model.WorkoutHistory;

@Component
public class WorkoutHistoryDAOImpl extends AbstractSpringDAO<WorkoutHistory> implements WorkoutHistoryDAO
{

    protected WorkoutHistoryDAOImpl(ORM<WorkoutHistory> orm, DataSource dataSource)
    {
        super(orm, dataSource);
    }

    @Autowired
    public WorkoutHistoryDAOImpl(DataSource datasource)
    {
        this(new WorkoutHistoryORM()
        {
        }, datasource);
    }

    public int clear()
    {
        return this.springTemplate.update(((WorkoutHistoryORM) this.orm).clear());
    }

    @Override
    public List<WorkoutHistory> readByUserId(Integer id)
    {
        List<WorkoutHistory> temp = new ArrayList<>();
        if (null == id)
            return null;
        return this.springTemplate.query(((WorkoutHistoryORM) this.orm).eagerPrepareReadByUserId(),
                (PreparedStatement ps) -> ps.setInt(1, id), (ResultSet cursor) -> {
                    while (cursor.next())
                        temp.add(((WorkoutHistoryORM) this.orm).eagerExerciseMap(cursor));
                    return temp;
                });
    }

    public Map<String, BigDecimal> calculateStatsbyUser(Integer id)
    {
        List<WorkoutHistory> temp = new ArrayList<>();
        Map<String, BigDecimal> map = new HashMap<>();

        BigDecimal[] distance =
        { BigDecimal.ZERO };
        BigDecimal[] weight =
        { BigDecimal.ZERO };
        BigDecimal[] time =
        { BigDecimal.ZERO };

        if (null == id)
            return null;
        return this.springTemplate.query(((WorkoutHistoryORM) this.orm).eagerPrepareReadByUserId(),
                (PreparedStatement ps) -> ps.setInt(1, id), (ResultSet cursor) -> {
                    while (cursor.next())
                        temp.add(((WorkoutHistoryORM) this.orm).eagerExerciseMap(cursor));

                    for (WorkoutHistory wh : temp)
                    {
                        if (wh.getDistance()==null)
                            distance[0] = distance[0].add(BigDecimal.ZERO);
                        else
                            distance[0] = distance[0].add(wh.getDistance());
                        if (wh.getTime()==null)
                            time[0] = time[0].add(BigDecimal.ZERO);
                        else
                            time[0] = time[0].add(wh.getTime());
                        
                        if (wh.getWeight()==null)
                            weight[0] = weight[0].add(BigDecimal.ZERO);
                        else{
                            weight[0] = weight[0].add((wh.getWeight().multiply(multiplier(wh.getReps())))
                                                      .multiply(multiplier(wh.getSets())));
                        }
                    }
                    map.put("distance", distance[0]);
                    map.put("time", time[0]);
                    map.put("weight", weight[0]);
                    return map;
                });
    }
    static BigDecimal multiplier(Integer input)
    {
        return input == null ? BigDecimal.ONE : BigDecimal.valueOf(input.longValue());
    }
    @Override
    public List<WorkoutHistory> readByUserIdDate(Integer id, LocalDate date)
    {
        List<WorkoutHistory> temp = new ArrayList<>();
        if (null == id)
            return null;
        return this.springTemplate.query(((WorkoutHistoryORM) this.orm).eagerPrepareReadByUserIdDate(),
                (PreparedStatement ps) -> {
                    ps.setInt(1, id);
                    ps.setDate(2, Date.valueOf(date));
                }, (ResultSet cursor) -> {
                    while (cursor.next())
                        temp.add(((WorkoutHistoryORM) this.orm).eagerExerciseMap(cursor));
                    return temp;
                });
    }

    public List<DateHolder> GetDateAndCategory(Integer id, LocalDate date1, LocalDate date2)
    {
        List<DateHolder> holderList = new ArrayList<>();
        Map<LocalDate, List<CategoryHolder>> temp = new HashMap<>();
        if (null == id)
            return null;
        return this.springTemplate.query(((WorkoutHistoryORM) this.orm).prepareDateAndCategory(),
                (PreparedStatement ps) -> {
                    ps.setInt(1, id);
                    ps.setDate(2, Date.valueOf(date1));
                    ps.setDate(3, Date.valueOf(date2));
                }, (ResultSet cursor) -> {
                    while (cursor.next())
                    {

                        CategoryHolder h = (((WorkoutHistoryORM) this.orm).categoryMap(cursor));
                        List<CategoryHolder> exists = temp.get(h.getDate());
                        if (exists == null)
                        {
                            List<CategoryHolder> list = new ArrayList<>();
                            list.add(h);
                            temp.put(h.getDate(), list);

                        } else
                            exists.add(h);

                    }
                    for (Entry<LocalDate, List<CategoryHolder>> item : temp.entrySet())
                    {
                        holderList.add(new DateHolder(item.getKey(), item.getValue()));

                    }

                    return holderList;
                });

    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, WorkoutHistory domainToInsert) throws SQLException
    {

        mapDomainToPreparedStatement(insertStatement, domainToInsert, 1);

    }

    @Override
    protected WorkoutHistory afterInsert(WorkoutHistory copy, Integer id)
    {
        WorkoutHistory wh = copy.clone();
        wh.setId(id);
        wh.setLoaded(true);
        return wh;
    }

    @Override
    protected WorkoutHistory afterUpdate(WorkoutHistory copy)
    {
        WorkoutHistory wh = copy.clone();
        wh.setLoaded(true);
        return wh;
    }

    @Override
    protected PreparedStatementSetter updatePreparer(WorkoutHistory domainToUpdate)
    {
        return (PreparedStatement ps) -> {

            int lastParameter = mapDomainToPreparedStatement(ps, domainToUpdate, 1);
            ps.setInt(lastParameter, domainToUpdate.getId());

        };
    }

    static int mapDomainToPreparedStatement(PreparedStatement preparedStatement, WorkoutHistory history,
            int parameterIndex) throws SQLException
    {

        preparedStatement.setDate(parameterIndex++, Date.valueOf(history.getWorkout_date()));
        handleIntNull(preparedStatement, parameterIndex++, history.getSets());
        handleIntNull(preparedStatement, parameterIndex++, history.getReps());
        preparedStatement.setBigDecimal(parameterIndex++, history.getWeight());
        preparedStatement.setBigDecimal(parameterIndex++, history.getDistance());
        preparedStatement.setBigDecimal(parameterIndex++, history.getTime());
        preparedStatement.setInt(parameterIndex++, history.getAccount().getId());
        preparedStatement.setInt(parameterIndex++, history.getExercise().getId());

        return parameterIndex;
    }

    static void handleIntNull(PreparedStatement ps, int parameterIndex, Integer value) throws SQLException
    {
        if (value == null)
        {
            ps.setNull(parameterIndex, Types.INTEGER);
        } else
            ps.setInt(parameterIndex, value);
    }

}
