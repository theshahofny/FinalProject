package com.ssa.ironyard.fitness.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.ssa.ironyard.fitness.model.Exercise;

@Component
public class ExerciseDAOImpl extends AbstractSpringDAO<Exercise> implements ExerciseDAO {

    protected ExerciseDAOImpl(ORM<Exercise> orm, DataSource dataSource) {
        super(orm, dataSource);
    }

    @Autowired
    public ExerciseDAOImpl(DataSource datasource) {
        this(new ExerciseORM() {
        }, datasource);
    }

    @Override
    public List<Exercise> readAll() {
        List<Exercise> temp = new ArrayList<>();
        return this.springTemplate.query(((ExerciseORM) this.orm).readAll(), (ResultSet cursor) -> {
            while (cursor.next())
                temp.add(this.orm.map(cursor));
            return temp;

        });
    }

    public int clear() {
        return this.springTemplate.update(((ExerciseORM) this.orm).clear());
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, Exercise domainToInsert) throws SQLException {

        mapDomainToPreparedStatement(insertStatement, domainToInsert, 1);

    }

    @Override
    protected Exercise afterInsert(Exercise copy, Integer id) {
        Exercise e = copy.clone();
        e.setId(id);
        e.setLoaded(true);
        return e;
    }

    @Override
    protected Exercise afterUpdate(Exercise copy) {
        Exercise e = copy.clone();
        e.setLoaded(true);
        return e;
    }

    @Override
    protected PreparedStatementSetter updatePreparer(Exercise domainToUpdate) {
        return (PreparedStatement ps) -> {
            
            int lastParameter = mapDomainToPreparedStatement(ps, domainToUpdate, 1);
            ps.setInt(lastParameter, domainToUpdate.getId());

        };
    }
    
    static int mapDomainToPreparedStatement(PreparedStatement preparedStatement, Exercise exercise, 
                                            int parameterIndex) throws SQLException
    {
        preparedStatement.setString(parameterIndex++, exercise.getExercise_name());
        preparedStatement.setString(parameterIndex++, exercise.getCategory().getData());
        preparedStatement.setString(parameterIndex++, exercise.getMuscles());
        preparedStatement.setString(parameterIndex++, exercise.getImage());
        preparedStatement.setString(parameterIndex++, exercise.getInstructions());
        return parameterIndex;
    }

}
