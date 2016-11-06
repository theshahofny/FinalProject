package com.ssa.ironyard.fitness.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

import com.ssa.ironyard.fitness.model.Goal;

public interface GoalORM extends ORM<Goal> {
    @Override
    default String projection() {
        StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
        joiner.add("id").add("goal");
        return joiner.toString();
    }

    @Override
    default String table() {
        return "goals";
    }

    @Override
    default Goal map(ResultSet results) {
        Goal g = new Goal();
        try {
            final String columnPrefix = table() + ".";
            g.setId(results.getInt(columnPrefix + "id"));
            g.setType(Goal.Type.getInstance(results.getString(columnPrefix + "goal")));
            g.setLoaded(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return g;
    }

    @Override
    default String prepareInsert() {
        return "INSERT INTO " + table() + " (" + projection().substring(10) + ") VALUES(?);";

    }

    @Override
    default String prepareUpdate() {
        return "UPDATE " + table() + "SET " + table() + ".goal = ? WHERE " + table() + ".id = ?";
    }

    @Override
    default String prepareRead() {
        return "SELECT " + projection() + " FROM " + table() + " WHERE " + table() + ".id = ?";
    }

    @Override
    default String prepareDelete() {
        return "DELETE FROM " + table() + " WHERE " + table() + ".id = ?";
    }

    default String clear() {
        return "DELETE FROM " + table();
    }
}
