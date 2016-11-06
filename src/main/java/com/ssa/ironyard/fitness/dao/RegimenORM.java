package com.ssa.ironyard.fitness.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

import com.ssa.ironyard.fitness.model.Account;
import com.ssa.ironyard.fitness.model.Exercise;
import com.ssa.ironyard.fitness.model.Regimen;
import com.ssa.ironyard.fitness.model.Regimen.DAY;

public interface RegimenORM extends ORM<Regimen> {
    @Override
    default String projection() {
        StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
        joiner.add("id").add("day").add("w_sets").add("reps").add("weight").add("distance").add("duration")
                .add("account_id").add("exercise_id");
        return joiner.toString();
    }

    @Override
    default String table() {
        return "regimen";
    }

    @Override
    default Regimen map(ResultSet results) {
        Regimen r = new Regimen();
        Account a = new Account();
        Exercise e = new Exercise();
        final String columnPrefix = table() + ".";

        try {
            a.setId(results.getInt(columnPrefix + "account_id"));
            r.setAccount(a);
            r.setDay(DAY.getInstance(results.getString("day")));
            r.setDistance(results.getBigDecimal(columnPrefix + "distance"));
            r.setExercise(e);
            r.setId(results.getInt(columnPrefix + "id"));
            if (results.wasNull())
                r.setId(null);
            r.setReps(results.getInt(columnPrefix + "reps"));
            if (results.wasNull())
                r.setReps(null);
            r.setSets(results.getInt(columnPrefix + "w_sets"));
            if (results.wasNull())
                r.setSets(null);
            r.setTime(results.getBigDecimal(columnPrefix + "duration"));
            r.setWeight(results.getBigDecimal(columnPrefix + "weight"));
            r.setLoaded(true);

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return r;

    }

    @Override
    default String prepareInsert() {
        return "INSERT INTO " + table() + " (" + projection().substring(12) + ") VALUES(?,?,?,?,?,?,?,?);";

    }

    @Override
    default String prepareUpdate() {
        return "UPDATE " + table() + " SET " + table() + ".workout_date = ?, " + table() + ".exercise_id = ?, "
                + table() + ".w_sets = ?, " + "" + table() + ".reps = ?, " + table() + ".weight =?, " + table()
                + ".distance = ?, " + table() + ".duration =?, WHERE " + table() + ".id = ?";
    }

    @Override
    default String prepareRead() {
        return "SELECT " + projection() + " FROM " + table() + " WHERE " + table() + ".id=?";

    }

    @Override
    default String prepareDelete() {
        return "DELETE FROM " + table() + " WHERE " + table() + ".id = ?";

    };

    default String clear() {
        return "DELETE FROM " + table();

    };

    default String eagerProjection() {
        return projection() + "," + (new ExerciseORM() {
        }.projection());
    };

    default String eagerPrepareReadByUserId() {
        return "SELECT " + eagerProjection() + " FROM " + table() + " INNER JOIN " + (new ExerciseORM() {
        }.table()) + " ON " + (new ExerciseORM() {
        }.table()) + ".id = " + table() + ".exercise_id WHERE " + table() + ".account_id= ?";
    };

    default Regimen eagerExerciseMap(ResultSet results) {
        Regimen r = map(results);
        r.setExercise(new ExerciseORM() {
        }.map(results));
        return r;
    }
}
