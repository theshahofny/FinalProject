package com.ssa.ironyard.fitness.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

import com.ssa.ironyard.fitness.model.Account;
import com.ssa.ironyard.fitness.model.CategoryHolder;
import com.ssa.ironyard.fitness.model.Exercise;
import com.ssa.ironyard.fitness.model.WorkoutHistory;

public interface WorkoutHistoryORM extends ORM<WorkoutHistory> {
    @Override
    default String projection() {

        StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
        joiner.add("id").add("workout_date").add("w_sets").add("reps").add("weight").add("distance").add("duration")
                .add("account_id").add("exercise_id");
        return joiner.toString();
    }

    @Override
    default String table() {
        return "history";
    }

    @Override
    default WorkoutHistory map(ResultSet results) {
        WorkoutHistory wh = new WorkoutHistory();
        Exercise e = new Exercise();
        Account a = new Account();
        try {

            final String columnPrefix = table() + ".";

            a.setId(results.getInt(columnPrefix + "account_id"));
            wh.setAccount(a);

            e.setId(results.getInt(columnPrefix + "exercise_id"));
            wh.setExercise(e);

            wh.setId(results.getInt(columnPrefix + "id"));
            wh.setWorkout_date(results.getDate(columnPrefix + "workout_date").toLocalDate());
            wh.setDistance(results.getBigDecimal(columnPrefix + "distance"));
            wh.setWeight(results.getBigDecimal(columnPrefix + "weight"));
            wh.setSets(results.getInt(columnPrefix + "w_sets"));
            if (results.wasNull())
                wh.setSets(null);
            wh.setReps(results.getInt(columnPrefix + "reps"));
            if (results.wasNull())
                wh.setReps(null);
            wh.setTime(results.getBigDecimal(columnPrefix + "duration"));
            wh.setLoaded(true);

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return wh;
    }

    default String eagerProjection() {
        return projection() + "," + (new ExerciseORM() {
        }.projection());
    };

    default String prepareReadByUsername() {
        return "SELECT " + projection() + " FROM " + table() + " WHERE " + table() + ".username=?";
    }

    default String eagerExerciseRead() {
        return "SELECT " + eagerProjection() + " FROM " + table() + " INNER JOIN " + new ExerciseORM() {
        }.table() + " ON " + new ExerciseORM() {
        }.table() + ".id = " + table() + ".exercise_id" + "WHERE" + table() + ".id = ?";

    }

    default String eagerPrepareReadByUserId() {
        return "SELECT " + eagerProjection() + " FROM " + table() + " INNER JOIN " + (new ExerciseORM() {
        }.table()) + " ON " + (new ExerciseORM() {
        }.table()) + ".id = " + table() + ".exercise_id WHERE " + table() + ".account_id= ?";
    };

    default WorkoutHistory eagerExerciseMap(ResultSet results) {
        WorkoutHistory wh = map(results);
        wh.setExercise(new ExerciseORM() {
        }.map(results));
        return wh;
    }

    @Override
    default String prepareInsert() {
        return "INSERT INTO " + table() + " (" + projection().substring(12) + ") VALUES(?,?,?,?,?,?,?,?);";
    }

    @Override
    default String prepareUpdate() {
        return "UPDATE " + table() + " SET " + table() + ".workout_date = ?, " + table() + ".exercise_id = ?, "
                + table() + ".w_sets = ?, " + "" + table() + ".reps = ?, " + table() + ".weight =?, " + table()
                + ".distance = ?, " + table() + ".duration =? " + "WHERE " + table() + ".id = ?";
    }

    @Override
    default String prepareRead() {

        return "SELECT " + projection() + " FROM " + table() + " WHERE " + table() + ".id=? order by " + table()
                + ".id asc";

    }

    @Override
    default String prepareDelete() {

        return "DELETE FROM " + table() + " WHERE " + table() + ".id = ?";

    }

    default String clear() {
        return "DELETE FROM " + table();

    }

    default String eagerPrepareReadByUserIdDate() {
        return "SELECT " + eagerProjection() + " FROM " + table() + " INNER JOIN " + (new ExerciseORM() {
        }.table()) + " ON " + (new ExerciseORM() {
        }.table()) + ".id = " + table() + ".exercise_id WHERE account_id=? AND workout_date=?";

    }
    
    default CategoryHolder categoryMap(ResultSet results) throws SQLException{
        CategoryHolder holder = new CategoryHolder();
        
        holder.setCat(Exercise.Category.getInstance(results.getString("category")));
        holder.setDate(results.getDate("workout_date").toLocalDate());
        
        
        return holder ;
        
        
    }

    default String prepareDateAndCategory() {
        return "SELECT DISTINCT workout_date,category FROM " + table() + " JOIN " + (new ExerciseORM() {
        }.table()) + " ON " + (new ExerciseORM() {
        }.table()) + ".id = " + table() + ".exercise_id WHERE " + table() + ".account_id= ? "
                + "and workout_date between ? and ? order by workout_date desc";
    };

}
