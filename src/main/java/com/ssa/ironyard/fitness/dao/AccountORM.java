package com.ssa.ironyard.fitness.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

import com.ssa.ironyard.fitness.model.Account;
import com.ssa.ironyard.fitness.model.Goal;
import com.ssa.ironyard.fitness.model.Password;

public interface AccountORM extends ORM<Account> {

    @Override
    default String projection() {
        StringJoiner joiner = new StringJoiner(", " + table() + ".", table() + ".", "");
        joiner.add("id").add("username").add("salt").add("hash").add("first_name").add("last_name").add("height")
                .add("weight").add("gender").add("age").add("goal_id");
        return joiner.toString();
    };

    @Override
    default String table() {

        return "accounts";
    }

    @Override
    default Account map(ResultSet results) {
        Account a = new Account();
        Goal g = new Goal();
        Password p;
        try {
            g.setId(results.getInt("goal_id"));
            a.setGoal(g);

            p = new Password(results.getString("salt"), results.getString("hash"));
            a.setPassword(p);

            a.setId(results.getInt("id"));
            a.setAge(results.getInt("age"));
            a.setFirstName(results.getString("first_name"));
            a.setLastName(results.getString("last_name"));
            a.setGender(Account.Gender.getInstance(results.getString("gender").charAt(0)));
            a.setHeight(results.getDouble("height"));
            a.setWeight(results.getDouble("weight"));
            a.setLoaded(true);
            a.setUsername(results.getString("username"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    };

    @Override
    default String prepareInsert() {
        return "INSERT INTO " + table() + " (" + projection().substring(13) + ") VALUES(?,?,?,?,?,?,?,?,?,?);";

    };

    @Override
    default String prepareUpdate() {
        return "UPDATE " + table() + " SET " + table() + ".username = ?, " + table() + ".password = ?, " + table()
                + ".first_name = ?, " + "" + table() + ".last_name = ?, " + table() + ".height =?, " + table()
                + ".weight = ?, " + table() + ".sex =?, " + table() + ".age = ?, " + table() + ".goal_id = ? "
                + "WHERE " + table() + ".id = ?";
    };

    default String prepareReadByUsername() {
        return "SELECT " + projection() + " FROM " + table() + " WHERE " + table() + ".username=?";
    }

    default String eagerPrepareReadByUsername() {
        return "SELECT " + eagerProjection() + " FROM " + table() + " INNER JOIN " + new GoalORM() {
        }.table() + " ON " + new GoalORM() {
        }.table() + ".id = " + table() + ".goal_id WHERE " + table() + ".username= ?";
    };

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
        return projection() + "," + new GoalORM() {
        }.projection();
    }

    default String prepareEagerRead() {
        return "SELECT " + eagerProjection() + " FROM " + table() + " INNER JOIN " + new GoalORM() {
        }.table() + " ON " + new GoalORM() {
        }.table() + "id = " + table() + ".goal_Id" + "WHERE" + table() + ".id = ?";
    };

    default Account eagerMap(ResultSet results) {

        Account a = map(results);
        a.setGoal(new GoalORM() {
        }.map(results));
        return a;
    };
}
