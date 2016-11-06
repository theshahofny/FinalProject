package com.ssa.ironyard.fitness.dao;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.ssa.ironyard.fitness.crypto.BCryptSecurePassword;
import com.ssa.ironyard.fitness.model.Account;
import com.ssa.ironyard.fitness.model.Exercise;
import com.ssa.ironyard.fitness.model.Exercise.Category;
import com.ssa.ironyard.fitness.model.Goal;
import com.ssa.ironyard.fitness.model.Password;
import com.ssa.ironyard.fitness.model.Regimen;
import com.ssa.ironyard.fitness.model.Regimen.DAY;

public class RegimenTests {

    static String URL = "jdbc:mysql://localhost/fitness?" + "user=root&password=root" + "&useServerPrepStmt=true";

    RegimenDAOImpl regimenDAO;
    ExerciseDAOImpl exerciseDAO;
    GoalDAOImpl goalDAO;
    AccountDAOimpl accountDAO;

    @Before
    public void setup() throws SQLException {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(URL);
        regimenDAO = new RegimenDAOImpl(mysqlDataSource);
        exerciseDAO = new ExerciseDAOImpl(mysqlDataSource);
        accountDAO = new AccountDAOimpl(mysqlDataSource);
        goalDAO = new GoalDAOImpl(mysqlDataSource);
        regimenDAO.clear();
        accountDAO.clear();
        exerciseDAO.clear();
        goalDAO.clear();
    }

    @Test
    public void regimenInsert() {

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

        Exercise e = new Exercise();
        e.setExercise_name("Push-ups");
        e.setCategory(Category.ARMS);
        e.setImage("http:image.com");
        e.setInstructions("Push up and then push down");
        e.setMuscles("Arms arms arms");
        e = exerciseDAO.insert(e);

        Regimen r = new Regimen();
        r.setAccount(a);
        r.setExercise(e);
        r.setDay(DAY.Friday);
        r.setDistance(new BigDecimal(5));
        r.setWeight(new BigDecimal(135));
        r.setReps(10);
        r.setSets(3);
        r.setTime(new BigDecimal(5.10));

        r = regimenDAO.insert(r);
        assertTrue(r.equals(regimenDAO.read(r.getId())));

    }

    @Test
    public void regimenReadByUserId() {

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

        Exercise e = new Exercise();
        e.setExercise_name("Push-ups");
        e.setCategory(Category.ARMS);
        e.setImage("http:image.com");
        e.setInstructions("Push up and then push down");
        e.setMuscles("Arms arms arms");
        e = exerciseDAO.insert(e);

        Regimen r = new Regimen();
        r.setAccount(a);
        r.setExercise(e);
        r.setDay(DAY.Friday);
        r.setDistance(new BigDecimal(5));
        r.setWeight(new BigDecimal(135));
        r.setReps(10);
        r.setSets(3);
        r.setTime(new BigDecimal(5.10));

        r = regimenDAO.insert(r);
        System.err.println(regimenDAO.readByUserId(a.getId()));

    }

}
