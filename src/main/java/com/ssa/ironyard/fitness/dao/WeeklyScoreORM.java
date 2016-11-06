package com.ssa.ironyard.fitness.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

import com.ssa.ironyard.fitness.model.Account;
import com.ssa.ironyard.fitness.model.WeeklyScore;

public interface WeeklyScoreORM extends ORM<WeeklyScore> {
    @Override
    default String projection() {

        StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
        joiner.add("id").add("week").add("account_id").add("score");
        return joiner.toString();
    }

    @Override
    default String table() {
        return "weekly_score";
    }

    @Override
    default WeeklyScore map(ResultSet results) {
        WeeklyScore ws = new WeeklyScore();
        Account a = new Account();
        try {

            final String columnPrefix = table() + ".";

            a.setId(results.getInt(columnPrefix + "account_id"));
            ws.setAccount(a);
            ws.setId(results.getInt(columnPrefix + "id"));
            ws.setScore(results.getDouble(columnPrefix + "score"));
            ws.setWeek(results.getInt(columnPrefix + "week"));
            ws.setLoaded(true);

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return ws;
    }

    default String eagerProjection() {
        return projection() + "," + (new AccountORM() {
        }.projection());
    };

    default String eagerPrepareReadByUserId() {
        return "SELECT " + eagerProjection() + " FROM " + table() + " INNER JOIN " + (new AccountORM() {
        }.table()) + " ON " + (new AccountORM() {
        }.table()) + ".id = " + table() + ".account_id WHERE " + table() + ".account_id= ?";
    };

    default WeeklyScore eagerMap(ResultSet results) {
        WeeklyScore ws = map(results);
        ws.setAccount(new AccountORM() {
        }.map(results));
        return ws;
    }

    @Override
    default String prepareInsert() {
        return "INSERT INTO " + table() + " (" + projection().substring(16) + ") VALUES(?,?,?);";
    }

    @Override
    default String prepareUpdate() {
        return "UPDATE " + table() + " SET " + table() + ".week = ?, " + table() + ".account_id = ?, " + table()
                + ".score = ?, WHERE " + table() + ".id = ?";
    }

    @Override
    default String prepareRead() {

        return "SELECT " + projection() + " FROM " + table() + " WHERE " + table() + ".id=?";

    }

    @Override
    default String prepareDelete() {

        return "DELETE FROM " + table() + " WHERE " + table() + ".id = ?";

    }

    default String clear() {
        return "DELETE FROM " + table();

    };

}
