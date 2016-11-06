package com.ssa.ironyard.fitness.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.ssa.ironyard.fitness.model.WeeklyScore;

public class WeeklyScoreDAOImpl extends AbstractSpringDAO<WeeklyScore> implements WeeklyScoreDAO {

    protected WeeklyScoreDAOImpl(ORM<WeeklyScore> orm, DataSource dataSource) {
        super(orm, dataSource);
    }

    @Autowired
    public WeeklyScoreDAOImpl(DataSource datasource) {
        this(new WeeklyScoreORM() {
        }, datasource);
    }

    @Override
    public List<WeeklyScore> readByUserId(Integer id) {
        List<WeeklyScore> temp = new ArrayList<>();
        if (null == id)
            return null;
        return this.springTemplate.query(((WeeklyScoreORM) this.orm).eagerPrepareReadByUserId(),
                (PreparedStatement ps) -> ps.setInt(1, id), (ResultSet cursor) -> {
                    while (cursor.next())
                        temp.add(((WeeklyScoreORM) this.orm).eagerMap(cursor));
                    return temp;
                });
    }

    public int clear() {
        return this.springTemplate.update(((WeeklyScoreORM) this.orm).clear());
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, WeeklyScore domainToInsert) throws SQLException {

        mapDomainToPreparedStatement(insertStatement, domainToInsert, 1);

    }

    @Override
    protected WeeklyScore afterInsert(WeeklyScore copy, Integer id) {
        WeeklyScore ws = copy;
        ws.setId(id);
        ws.setLoaded(true);
        return ws;
    }

    @Override
    protected WeeklyScore afterUpdate(WeeklyScore copy) {
        WeeklyScore ws = copy;
        ws.setLoaded(true);
        return ws;
    }

    @Override
    protected PreparedStatementSetter updatePreparer(WeeklyScore domainToUpdate) {
        return (PreparedStatement ps) -> {
            int lastParameter = mapDomainToPreparedStatement(ps, domainToUpdate, 1);
            ps.setInt(lastParameter, domainToUpdate.getId());
        };
    }

    static int mapDomainToPreparedStatement(PreparedStatement preparedStatement, WeeklyScore score, int parameterIndex)
            throws SQLException {

        preparedStatement.setInt(parameterIndex++, score.getWeek());
        preparedStatement.setInt(parameterIndex++, score.getAccount().getId());
        preparedStatement.setDouble(parameterIndex++, score.getScore());
        return parameterIndex;
    }

}
