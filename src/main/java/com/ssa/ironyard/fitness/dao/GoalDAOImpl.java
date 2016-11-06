package com.ssa.ironyard.fitness.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.ssa.ironyard.fitness.model.Goal;

@Component
public class GoalDAOImpl extends AbstractSpringDAO<Goal> implements GoalDAO {

    protected GoalDAOImpl(ORM<Goal> orm, DataSource dataSource) {
        super(orm, dataSource);
    }

    @Autowired
    public GoalDAOImpl(DataSource datasource) {
        this(new GoalORM() {
        }, datasource);
    }

    public int clear() {
        return this.springTemplate.update(((GoalORM) this.orm).clear());
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, Goal domainToInsert) throws SQLException {

        mapDomainToPreparedStatement(insertStatement, domainToInsert, 1);

    }

    @Override
    protected Goal afterInsert(Goal copy, Integer id) {
        Goal g = copy.clone();
        g.setId(id);
        g.setLoaded(true);
        return g;
    }

    @Override
    protected Goal afterUpdate(Goal copy) {
        Goal g = copy.clone();
        g.setLoaded(true);
        return g;
    }

    @Override
    protected PreparedStatementSetter updatePreparer(Goal domainToUpdate) {
        return (PreparedStatement ps) -> {
            int lastParameter = mapDomainToPreparedStatement(ps, domainToUpdate, 1);
            ps.setInt(lastParameter, domainToUpdate.getId());
        };
    }

    static int mapDomainToPreparedStatement(PreparedStatement preparedStatement, Goal goal, int parameterIndex)
            throws SQLException {

        preparedStatement.setString(parameterIndex++, (goal.getType().abbrev));

        return parameterIndex;
    }

}
