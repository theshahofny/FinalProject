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

public class AccountTests {
    
    static String URL = "jdbc:mysql://localhost/fitness?" + "user=root&password=root" + "&useServerPrepStmt=true";
    
    
    AccountDAOimpl accountDAO;
    GoalDAOImpl goalDAO;
    WeeklyScoreDAOImpl weeklyScoresDAO;
    
    @Before
    public void setup() throws SQLException {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(URL);
        accountDAO = new AccountDAOimpl(mysqlDataSource);
        goalDAO = new GoalDAOImpl(mysqlDataSource);
        weeklyScoresDAO = new  WeeklyScoreDAOImpl(mysqlDataSource);
      
        weeklyScoresDAO.clear();
        accountDAO.clear();
        goalDAO.clear();
    }
    
    @Test
    public void accountInsert(){
        
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
        
        System.err.println(accountDAO.read(a.getId()));
        
        
        assertTrue(a.deeplyEquals(accountDAO.read(a.getId())));
                
    }
    
    
    @Test
    public void accountReadByUsername(){
        
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
        
        assertTrue(a.deeplyEquals(accountDAO.readByUsername(a.getUsername())));
        

        
    }

}
