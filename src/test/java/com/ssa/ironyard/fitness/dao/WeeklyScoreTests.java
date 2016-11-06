package com.ssa.ironyard.fitness.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.ssa.ironyard.fitness.crypto.BCryptSecurePassword;
import com.ssa.ironyard.fitness.dao.AccountDAOimpl;
import com.ssa.ironyard.fitness.dao.GoalDAOImpl;
import com.ssa.ironyard.fitness.dao.WeeklyScoreDAOImpl;
import com.ssa.ironyard.fitness.model.Account;
import com.ssa.ironyard.fitness.model.Goal;
import com.ssa.ironyard.fitness.model.Password;
import com.ssa.ironyard.fitness.model.WeeklyScore;

public class WeeklyScoreTests {
    static String URL = "jdbc:mysql://localhost/fitness?" + "user=root&password=root" + "&useServerPrepStmt=true";
    WeeklyScoreDAOImpl weeklyScoreDAO;
    GoalDAOImpl goalDAO;
    AccountDAOimpl accountDAO;

    @Before
    public void setup() throws SQLException {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(URL);
        weeklyScoreDAO = new WeeklyScoreDAOImpl(mysqlDataSource);
        accountDAO = new AccountDAOimpl(mysqlDataSource);
        goalDAO = new GoalDAOImpl(mysqlDataSource);
        weeklyScoreDAO.clear();
        accountDAO.clear();
        goalDAO.clear();
    }

    @Test
    public void weeklyScoreInsert() {
        Goal g = new Goal();
        g.setType(Goal.Type.Endurance);
        g = goalDAO.insert(g);
        
        BCryptSecurePassword crypt = new BCryptSecurePassword();
        Password p = crypt.secureHash("password");
       
        Account a = new Account();
        a.setAge(18);
        a.setFirstName("David");
        a.setLastName("Shea");
        a.setGender(Account.Gender.Male);
        a.setHeight(6.00);
        a.setWeight(300.4);
        a.setUsername("fitness123");
        a.setGoal(g);
        a.setPassword(p);
        
        a = accountDAO.insert(a);
        
        WeeklyScore ws = new WeeklyScore();
        ws.setAccount(a);
        ws.setScore(80.5);
        ws.setWeek(1);
        
        ws = weeklyScoreDAO.insert(ws);
        
        assertTrue(ws.equals(weeklyScoreDAO.read(ws.getId())));
       
           }
}
