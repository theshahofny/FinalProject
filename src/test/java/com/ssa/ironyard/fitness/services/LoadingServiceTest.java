package com.ssa.ironyard.fitness.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.ssa.ironyard.fitness.crypto.BCryptSecurePassword;
import com.ssa.ironyard.fitness.dao.AccountDAOimpl;
import com.ssa.ironyard.fitness.dao.ExerciseDAOImpl;
import com.ssa.ironyard.fitness.dao.GoalDAOImpl;
import com.ssa.ironyard.fitness.dao.RegimenDAOImpl;
import com.ssa.ironyard.fitness.dao.WeeklyScoreDAOImpl;
import com.ssa.ironyard.fitness.dao.WorkoutHistoryDAOImpl;
import com.ssa.ironyard.fitness.model.Account;
import com.ssa.ironyard.fitness.model.Exercise;
import com.ssa.ironyard.fitness.model.Exercise.Category;
import com.ssa.ironyard.fitness.model.Goal;
import com.ssa.ironyard.fitness.model.Password;
import com.ssa.ironyard.fitness.model.Regimen;
import com.ssa.ironyard.fitness.model.Regimen.DAY;
import com.ssa.ironyard.fitness.model.WorkoutHistory;
import com.ssa.ironyard.fitness.utils.FileLoadingService;

public class LoadingServiceTest {

    static String URL = "jdbc:mysql://localhost/fitness?" + "user=root&password=root" + "&useServerPrepStmt=true";

    AccountDAOimpl accountDAO;
    GoalDAOImpl goalDAO;
    WeeklyScoreDAOImpl weeklyScoresDAO;
    RegimenDAOImpl regimenDAO;
    ExerciseDAOImpl exerciseDAO;
    WorkoutHistoryDAOImpl workoutHistoryDAO;

    @Before
    public void setup() throws SQLException {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(URL);
        workoutHistoryDAO = new WorkoutHistoryDAOImpl(mysqlDataSource);
        regimenDAO = new RegimenDAOImpl(mysqlDataSource);
        exerciseDAO = new ExerciseDAOImpl(mysqlDataSource);
        accountDAO = new AccountDAOimpl(mysqlDataSource);
        goalDAO = new GoalDAOImpl(mysqlDataSource);
        weeklyScoresDAO = new WeeklyScoreDAOImpl(mysqlDataSource);
        workoutHistoryDAO.clear();
        regimenDAO.clear();
        exerciseDAO.clear();
        weeklyScoresDAO.clear();
        accountDAO.clear();
        goalDAO.clear();
    }

    @Test
    public void initializeDatabase() throws URISyntaxException, IOException {
        FileLoadingService fls = new FileLoadingService();
        fls.intialize();
        newUser();
        user1();
        //averageUser();

    }

    private void newUser() {

        Goal g = new Goal();
        g.setType(Goal.Type.Endurance);
        g = goalDAO.insert(g);

        BCryptSecurePassword crypt = new BCryptSecurePassword();
        Password p = crypt.secureHash("password");

        Account a = new Account();
        a.setAge(18);
        a.setFirstName("Todd");
        a.setLastName("Dumbo");
        a.setGender(Account.Gender.Male);
        a.setHeight(6.10);
        a.setWeight(300.4);
        a.setUsername("fitness123");
        a.setGoal(g);
        a.setPassword(p);

        a = accountDAO.insert(a);

    }

    private void user1() {

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
        a.setHeight(6.10);
        a.setWeight(300.4);
        a.setUsername("dshea2");
        a.setGoal(g);
        a.setPassword(p);

        a = accountDAO.insert(a);

        // Monday/Wednesday Exercises

        Exercise e = new Exercise();
        e.setExercise_name("Push-ups");
        e.setCategory(Category.ARMS);
        e.setImage("http:image.com");
        e.setInstructions("Push up and then push down");
        e.setMuscles("Brachialis");
        e = exerciseDAO.insert(e);

        Exercise e2 = new Exercise();
        e2.setExercise_name("Pull-ups");
        e2.setCategory(Category.ARMS);
        e2.setImage("http:image.com");
        e2.setInstructions("Push up and then push down");
        e2.setMuscles("Deltoid");
        e2 = exerciseDAO.insert(e2);

        Exercise e3 = new Exercise();
        e3.setExercise_name("Sit-ups");
        e3.setCategory(Category.CORE);
        e3.setImage("http:image.com");
        e3.setInstructions("Push up and then push down");
        e3.setMuscles("Rectus Abdominis");
        e3 = exerciseDAO.insert(e3);

        Regimen r = new Regimen();
        r.setAccount(a);
        r.setExercise(e);
        r.setDay(DAY.Monday);
        r.setReps(20);
        r.setSets(3);

        r = regimenDAO.insert(r);

        // Monday Workout

        Regimen r2 = new Regimen();
        r2.setAccount(a);
        r2.setExercise(e2);
        r2.setDay(DAY.Monday);
        r2.setReps(5);
        r2.setSets(3);

        r2 = regimenDAO.insert(r2);

        Regimen r3 = new Regimen();
        r3.setAccount(a);
        r3.setExercise(e3);
        r3.setDay(DAY.Monday);
        r3.setReps(40);
        r3.setSets(3);

        r3 = regimenDAO.insert(r3);

        // Wednesday workout

        Regimen r4 = new Regimen();
        r4.setAccount(a);
        r4.setExercise(e3);
        r4.setDay(DAY.Wednesday);
        r4.setReps(40);
        r4.setSets(3);

        r4 = regimenDAO.insert(r4);

        Regimen r5 = new Regimen();
        r5.setAccount(a);
        r5.setExercise(e2);
        r5.setDay(DAY.Wednesday);
        r5.setReps(5);
        r5.setSets(3);

        r5 = regimenDAO.insert(r5);

        Regimen r6 = new Regimen();
        r6.setAccount(a);
        r6.setExercise(e);
        r6.setDay(DAY.Wednesday);
        r6.setReps(20);
        r6.setSets(3);

        r6 = regimenDAO.insert(r6);

        // Tues/Thurs exercises

        Exercise e4 = new Exercise();
        e4.setExercise_name("Squat");
        e4.setCategory(Category.LEGS);
        e4.setImage("http:image.com");
        e4.setInstructions("Squat");
        e4.setMuscles("Everything in your Legs");
        e4 = exerciseDAO.insert(e4);

        Exercise e5 = new Exercise();
        e5.setExercise_name("Run");
        e5.setCategory(Category.CARDIO);
        e5.setImage("http:image.com");
        e5.setInstructions("Run");
        e5.setMuscles("everything");
        e5 = exerciseDAO.insert(e5);

        // Tuesday Workout

        Regimen r7 = new Regimen();
        r7.setAccount(a);
        r7.setExercise(e4);
        r7.setDay(DAY.Tuesday);
        r7.setWeight(new BigDecimal(180.00));
        r7.setReps(10);
        r7.setSets(3);

        r7 = regimenDAO.insert(r7);

        Regimen r8 = new Regimen();
        r8.setAccount(a);
        r8.setExercise(e5);
        r8.setDay(DAY.Tuesday);
        r8.setDistance(new BigDecimal(1.5));
        r8.setTime(new BigDecimal(12.00));

        r8 = regimenDAO.insert(r8);

        // Thursday Workout

        Regimen r9 = new Regimen();
        r9.setAccount(a);
        r9.setExercise(e4);
        r9.setDay(DAY.Thursday);
        r9.setWeight(new BigDecimal(180.00));
        r9.setReps(10);
        r9.setSets(3);

        r9 = regimenDAO.insert(r9);

        Regimen r10 = new Regimen();
        r10.setAccount(a);
        r10.setExercise(e5);
        r10.setDay(DAY.Thursday);
        r10.setDistance(new BigDecimal(1.5));
        r10.setTime(new BigDecimal(12.00));

        r10 = regimenDAO.insert(r10);

        // Friday exercises

        Exercise e6 = new Exercise();
        e6.setExercise_name("Bench Press");
        e6.setCategory(Category.CHEST);
        e6.setImage("http:image.com");
        e6.setInstructions("Lift");
        e6.setMuscles("Pectoralis");
        e6 = exerciseDAO.insert(e6);

        Exercise e7 = new Exercise();
        e7.setExercise_name("Alternating Bicep Curls");
        e7.setCategory(Category.ARMS);
        e7.setImage("http:image.com");
        e7.setInstructions("Alternate curls.");
        e7.setMuscles("Biceps");
        e7 = exerciseDAO.insert(e7);

        Exercise e8 = new Exercise();
        e8.setExercise_name("Dips");
        e8.setCategory(Category.ARMS);
        e8.setImage("http:image.com");
        e8.setInstructions("Run");
        e8.setMuscles("everything");
        e8 = exerciseDAO.insert(e8);

        // Friday workout

        Regimen r11 = new Regimen();
        r11.setAccount(a);
        r11.setExercise(e6);
        r11.setDay(DAY.Friday);
        r11.setWeight(new BigDecimal(115.00));
        r11.setReps(8);
        r11.setSets(3);

        r11 = regimenDAO.insert(r11);

        Regimen r12 = new Regimen();
        r12.setAccount(a);
        r12.setExercise(e6);
        r12.setDay(DAY.Friday);
        r12.setWeight(new BigDecimal(135.00));
        r12.setReps(4);
        r12.setSets(3);

        r12 = regimenDAO.insert(r12);

        Regimen r13 = new Regimen();
        r13.setAccount(a);
        r13.setExercise(e7);
        r13.setDay(DAY.Friday);
        r13.setWeight(new BigDecimal(25));
        r13.setReps(20);
        r13.setSets(3);

        r13 = regimenDAO.insert(r13);

        Regimen r15 = new Regimen();
        r15.setAccount(a);
        r15.setExercise(e7);
        r15.setDay(DAY.Friday);
        r15.setWeight(new BigDecimal(35));
        r15.setReps(10);
        r15.setSets(3);

        r13 = regimenDAO.insert(r15);

        Regimen r14 = new Regimen();
        r14.setAccount(a);
        r14.setExercise(e8);
        r14.setDay(DAY.Friday);
        r14.setWeight(new BigDecimal(25));
        r14.setReps(10);
        r14.setSets(3);

        r14 = regimenDAO.insert(r14);

        // Saturday

        Regimen r16 = new Regimen();
        r16.setAccount(a);
        r16.setExercise(e5);
        r16.setDay(DAY.Saturday);
        r16.setDistance(new BigDecimal(2));
        r16.setTime(new BigDecimal(16.07));

        r16 = regimenDAO.insert(r16);

        // Thursday Sept 1
        WorkoutHistory wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 1));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(8);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 1));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(12.09));

        wh = workoutHistoryDAO.insert(wh);

        // Friday Sept 2

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 2));
        wh.setWeight(new BigDecimal(115));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 2));
        wh.setWeight(new BigDecimal(135));
        wh.setReps(2);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 9, 2));
        wh.setWeight(new BigDecimal(25));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 9, 2));
        wh.setWeight(new BigDecimal(35));
        wh.setReps(6);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e8);
        wh.setWorkout_date(LocalDate.of(2016, 9, 2));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Monday Septemeber 5th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 5));
        wh.setReps(20);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 5));
        wh.setReps(10);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 5));
        wh.setReps(3);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 5));
        wh.setReps(1);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 5));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Tuesday September 6th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 6));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 6));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Wednesday September 7th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 7));
        wh.setReps(20);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 7));
        wh.setReps(12);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 7));
        wh.setReps(4);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 7));
        wh.setReps(1);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 7));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Thursday September 8th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 8));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 8));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Friday September 9th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 9));
        wh.setWeight(new BigDecimal(115));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 9));
        wh.setWeight(new BigDecimal(135));
        wh.setReps(2);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 9, 9));
        wh.setWeight(new BigDecimal(25));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 9, 9));
        wh.setWeight(new BigDecimal(35));
        wh.setReps(6);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e8);
        wh.setWorkout_date(LocalDate.of(2016, 9, 9));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Saturday September 10

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 10));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Monday September 12

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 12));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 12));
        wh.setReps(3);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 12));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Tuesday September 13

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 13));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 13));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Wednesday September 14

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 14));
        wh.setReps(20);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 14));
        wh.setReps(12);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 14));
        wh.setReps(4);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 14));
        wh.setReps(1);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 14));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Thursday September 15

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 15));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 15));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Friday September 16

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 16));
        wh.setWeight(new BigDecimal(115));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 16));
        wh.setWeight(new BigDecimal(135));
        wh.setReps(2);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        // Saturday September 17

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 17));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Monday September 19

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 19));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 19));
        wh.setReps(3);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 19));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Tuesday September 20th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 20));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 20));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Wednesday September 21

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 21));
        wh.setReps(20);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 21));
        wh.setReps(12);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 21));
        wh.setReps(4);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 21));
        wh.setReps(1);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 21));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Thursday September 22

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 22));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);
        wh.setTime(new BigDecimal(15.30));

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 22));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Friday September 23

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 23));
        wh.setWeight(new BigDecimal(115));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 23));
        wh.setWeight(new BigDecimal(135));
        wh.setReps(2);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        // Saturday September 24

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 24));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.30));

        wh = workoutHistoryDAO.insert(wh);

        // Monday September 26

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 26));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 26));
        wh.setReps(3);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 26));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Tuesday September 27th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 27));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 27));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Wednesday September 28

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 28));
        wh.setReps(20);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 28));
        wh.setReps(12);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 28));
        wh.setReps(4);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 28));
        wh.setReps(1);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 28));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Thursday September 29

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 29));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);
        wh.setTime(new BigDecimal(15.30));

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 29));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Friday September 30

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 30));
        wh.setWeight(new BigDecimal(115));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 30));
        wh.setWeight(new BigDecimal(135));
        wh.setReps(2);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        // Saturday October 1st

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 1));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.30));

        wh = workoutHistoryDAO.insert(wh);

        // Monday October 3rd

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 10, 3));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 10, 3));
        wh.setReps(3);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 10, 3));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Tuesday October 4th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 10, 4));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 4));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(10.45));

        wh = workoutHistoryDAO.insert(wh);

        // Wednesday October 5th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 10, 5));
        wh.setReps(20);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 10, 5));
        wh.setReps(15);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 10, 5));
        wh.setReps(3);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 10, 5));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Thursday October 6th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 10, 6));
        wh.setWeight(new BigDecimal(155.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 6));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(10.45));

        wh = workoutHistoryDAO.insert(wh);

        // Friday October 7th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 10, 7));
        wh.setWeight(new BigDecimal(115));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 10, 7));
        wh.setWeight(new BigDecimal(135));
        wh.setReps(2);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 10, 7));
        wh.setWeight(new BigDecimal(25));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 10, 7));
        wh.setWeight(new BigDecimal(35));
        wh.setReps(6);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e8);
        wh.setWorkout_date(LocalDate.of(2016, 10, 7));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Saturday October 8

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 8));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Monday October 10

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 10, 10));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 10, 10));
        wh.setReps(3);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 10, 10));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Tuesday October 4th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 10, 10));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 10));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(10.45));

        wh = workoutHistoryDAO.insert(wh);

        // Wednesday October 11th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 10, 11));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 10, 11));
        wh.setReps(5);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 10, 11));
        wh.setReps(40);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Thursday October 12th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 10, 12));
        wh.setWeight(new BigDecimal(165.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 12));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(10.45));

        wh = workoutHistoryDAO.insert(wh);

        // Friday October 13th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 10, 13));
        wh.setWeight(new BigDecimal(115));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 10, 13));
        wh.setWeight(new BigDecimal(135));
        wh.setReps(4);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 10, 13));
        wh.setWeight(new BigDecimal(25));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 10, 13));
        wh.setWeight(new BigDecimal(35));
        wh.setReps(6);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e8);
        wh.setWorkout_date(LocalDate.of(2016, 10, 13));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Saturday October 14

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 14));
        wh.setDistance(new BigDecimal(2.0));
        wh.setTime(new BigDecimal(14.15));

        wh = workoutHistoryDAO.insert(wh);

        // Monday October 16

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 10, 16));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 10, 16));
        wh.setReps(3);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 10, 16));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Tuesday October 17th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 10, 17));
        wh.setWeight(new BigDecimal(165.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 17));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(10.45));

        wh = workoutHistoryDAO.insert(wh);

        // Wednesday October 18th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 10, 18));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 10, 18));
        wh.setReps(5);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 10, 18));
        wh.setReps(40);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);
    }
    
    private void averageUser() {
        
        Goal g = new Goal();
        g.setType(Goal.Type.Endurance);
        g = goalDAO.insert(g);

        BCryptSecurePassword crypt = new BCryptSecurePassword();
        Password p = crypt.secureHash("password");

        Account a = new Account();
        a.setAge(18);
        a.setFirstName("Average");
        a.setLastName("Joe");
        a.setGender(Account.Gender.Male);
        a.setHeight(6.10);
        a.setWeight(300.4);
        a.setUsername("averageJoe");
        a.setGoal(g);
        a.setPassword(p);

        a = accountDAO.insert(a);
        
        // Monday/Wednesday Exercises

        Exercise e = new Exercise();
        e.setExercise_name("Push-ups");
        e.setCategory(Category.ARMS);
        e.setImage("http:image.com");
        e.setInstructions("Push up and then push down");
        e.setMuscles("Brachialis");
        e = exerciseDAO.insert(e);

        Exercise e2 = new Exercise();
        e2.setExercise_name("Pull-ups");
        e2.setCategory(Category.ARMS);
        e2.setImage("http:image.com");
        e2.setInstructions("Push up and then push down");
        e2.setMuscles("Deltoid");
        e2 = exerciseDAO.insert(e2);

        Exercise e3 = new Exercise();
        e3.setExercise_name("Sit-ups");
        e3.setCategory(Category.CORE);
        e3.setImage("http:image.com");
        e3.setInstructions("Push up and then push down");
        e3.setMuscles("Rectus Abdominis");
        e3 = exerciseDAO.insert(e3);

        Regimen r = new Regimen();
        r.setAccount(a);
        r.setExercise(e);
        r.setDay(DAY.Monday);
        r.setReps(20);
        r.setSets(3);

        r = regimenDAO.insert(r);

        // Monday Workout

        Regimen r2 = new Regimen();
        r2.setAccount(a);
        r2.setExercise(e2);
        r2.setDay(DAY.Monday);
        r2.setReps(5);
        r2.setSets(3);

        r2 = regimenDAO.insert(r2);

        Regimen r3 = new Regimen();
        r3.setAccount(a);
        r3.setExercise(e3);
        r3.setDay(DAY.Monday);
        r3.setReps(40);
        r3.setSets(3);

        r3 = regimenDAO.insert(r3);

        // Wednesday workout

        Regimen r4 = new Regimen();
        r4.setAccount(a);
        r4.setExercise(e3);
        r4.setDay(DAY.Wednesday);
        r4.setReps(40);
        r4.setSets(3);

        r4 = regimenDAO.insert(r4);

        Regimen r5 = new Regimen();
        r5.setAccount(a);
        r5.setExercise(e2);
        r5.setDay(DAY.Wednesday);
        r5.setReps(5);
        r5.setSets(3);

        r5 = regimenDAO.insert(r5);

        Regimen r6 = new Regimen();
        r6.setAccount(a);
        r6.setExercise(e);
        r6.setDay(DAY.Wednesday);
        r6.setReps(20);
        r6.setSets(3);

        r6 = regimenDAO.insert(r6);

        // Tues/Thurs exercises

        Exercise e4 = new Exercise();
        e4.setExercise_name("Squat");
        e4.setCategory(Category.LEGS);
        e4.setImage("http:image.com");
        e4.setInstructions("Squat");
        e4.setMuscles("Everything in your Legs");
        e4 = exerciseDAO.insert(e4);

        Exercise e5 = new Exercise();
        e5.setExercise_name("Run");
        e5.setCategory(Category.CARDIO);
        e5.setImage("http:image.com");
        e5.setInstructions("Run");
        e5.setMuscles("everything");
        e5 = exerciseDAO.insert(e5);

        // Tuesday Workout

        Regimen r7 = new Regimen();
        r7.setAccount(a);
        r7.setExercise(e4);
        r7.setDay(DAY.Tuesday);
        r7.setWeight(new BigDecimal(180.00));
        r7.setReps(10);
        r7.setSets(3);

        r7 = regimenDAO.insert(r7);

        Regimen r8 = new Regimen();
        r8.setAccount(a);
        r8.setExercise(e5);
        r8.setDay(DAY.Tuesday);
        r8.setDistance(new BigDecimal(1.5));
        r8.setTime(new BigDecimal(12.00));

        r8 = regimenDAO.insert(r8);

        // Thursday Workout

        Regimen r9 = new Regimen();
        r9.setAccount(a);
        r9.setExercise(e4);
        r9.setDay(DAY.Thursday);
        r9.setWeight(new BigDecimal(180.00));
        r9.setReps(10);
        r9.setSets(3);

        r9 = regimenDAO.insert(r9);

        Regimen r10 = new Regimen();
        r10.setAccount(a);
        r10.setExercise(e5);
        r10.setDay(DAY.Thursday);
        r10.setDistance(new BigDecimal(1.5));
        r10.setTime(new BigDecimal(12.00));

        r10 = regimenDAO.insert(r10);

        // Friday exercises

        Exercise e6 = new Exercise();
        e6.setExercise_name("Bench Press");
        e6.setCategory(Category.CHEST);
        e6.setImage("http:image.com");
        e6.setInstructions("Lift");
        e6.setMuscles("Pectoralis");
        e6 = exerciseDAO.insert(e6);

        Exercise e7 = new Exercise();
        e7.setExercise_name("Alternating Bicep Curls");
        e7.setCategory(Category.ARMS);
        e7.setImage("http:image.com");
        e7.setInstructions("Alternate curls.");
        e7.setMuscles("Biceps");
        e7 = exerciseDAO.insert(e7);

        Exercise e8 = new Exercise();
        e8.setExercise_name("Dips");
        e8.setCategory(Category.ARMS);
        e8.setImage("http:image.com");
        e8.setInstructions("Run");
        e8.setMuscles("everything");
        e8 = exerciseDAO.insert(e8);

        // Friday workout

        Regimen r11 = new Regimen();
        r11.setAccount(a);
        r11.setExercise(e6);
        r11.setDay(DAY.Friday);
        r11.setWeight(new BigDecimal(115.00));
        r11.setReps(8);
        r11.setSets(3);

        r11 = regimenDAO.insert(r11);

        Regimen r12 = new Regimen();
        r12.setAccount(a);
        r12.setExercise(e6);
        r12.setDay(DAY.Friday);
        r12.setWeight(new BigDecimal(135.00));
        r12.setReps(4);
        r12.setSets(3);

        r12 = regimenDAO.insert(r12);

        Regimen r13 = new Regimen();
        r13.setAccount(a);
        r13.setExercise(e7);
        r13.setDay(DAY.Friday);
        r13.setWeight(new BigDecimal(25));
        r13.setReps(20);
        r13.setSets(3);

        r13 = regimenDAO.insert(r13);

        Regimen r15 = new Regimen();
        r15.setAccount(a);
        r15.setExercise(e7);
        r15.setDay(DAY.Friday);
        r15.setWeight(new BigDecimal(35));
        r15.setReps(10);
        r15.setSets(3);

        r13 = regimenDAO.insert(r15);

        Regimen r14 = new Regimen();
        r14.setAccount(a);
        r14.setExercise(e8);
        r14.setDay(DAY.Friday);
        r14.setWeight(new BigDecimal(25));
        r14.setReps(10);
        r14.setSets(3);

        r14 = regimenDAO.insert(r14);

        // Saturday

        Regimen r16 = new Regimen();
        r16.setAccount(a);
        r16.setExercise(e5);
        r16.setDay(DAY.Saturday);
        r16.setDistance(new BigDecimal(2));
        r16.setTime(new BigDecimal(16.07));

        r16 = regimenDAO.insert(r16);

        // Thursday Sept 1
        WorkoutHistory wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 1));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(8);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 1));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(12.09));

        wh = workoutHistoryDAO.insert(wh);

        // Friday Sept 2

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 2));
        wh.setWeight(new BigDecimal(115));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 2));
        wh.setWeight(new BigDecimal(135));
        wh.setReps(2);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 9, 2));
        wh.setWeight(new BigDecimal(25));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 9, 2));
        wh.setWeight(new BigDecimal(35));
        wh.setReps(6);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e8);
        wh.setWorkout_date(LocalDate.of(2016, 9, 2));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Monday Septemeber 5th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 5));
        wh.setReps(20);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 5));
        wh.setReps(10);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 5));
        wh.setReps(3);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 5));
        wh.setReps(1);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 5));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Tuesday September 6th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 6));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 6));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Wednesday September 7th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 7));
        wh.setReps(20);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 7));
        wh.setReps(12);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 7));
        wh.setReps(4);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 7));
        wh.setReps(1);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 7));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Thursday September 8th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 8));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 8));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Friday September 9th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 9));
        wh.setWeight(new BigDecimal(115));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 9));
        wh.setWeight(new BigDecimal(135));
        wh.setReps(2);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 9, 9));
        wh.setWeight(new BigDecimal(25));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 9, 9));
        wh.setWeight(new BigDecimal(35));
        wh.setReps(6);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e8);
        wh.setWorkout_date(LocalDate.of(2016, 9, 9));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Saturday September 10

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 10));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Monday September 12

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 12));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 12));
        wh.setReps(3);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 12));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Tuesday September 13

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 13));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 13));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Wednesday September 14

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 14));
        wh.setReps(20);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 14));
        wh.setReps(12);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 14));
        wh.setReps(4);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 14));
        wh.setReps(1);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 14));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Thursday September 15

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 15));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 15));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Friday September 16

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 16));
        wh.setWeight(new BigDecimal(115));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 16));
        wh.setWeight(new BigDecimal(135));
        wh.setReps(2);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        // Saturday September 17

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 17));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Monday September 19

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 19));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 19));
        wh.setReps(3);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 19));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Tuesday September 20th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 20));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 20));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Wednesday September 21

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 21));
        wh.setReps(20);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 21));
        wh.setReps(12);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 21));
        wh.setReps(4);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 21));
        wh.setReps(1);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 21));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Thursday September 22

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 22));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);
        wh.setTime(new BigDecimal(15.30));

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 22));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Friday September 23

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 23));
        wh.setWeight(new BigDecimal(115));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 23));
        wh.setWeight(new BigDecimal(135));
        wh.setReps(2);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        // Saturday September 24

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 24));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.30));

        wh = workoutHistoryDAO.insert(wh);

        // Monday September 26

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 26));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 26));
        wh.setReps(3);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 26));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Tuesday September 27th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 27));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 27));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Wednesday September 28

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 28));
        wh.setReps(20);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 9, 28));
        wh.setReps(12);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 28));
        wh.setReps(4);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 9, 28));
        wh.setReps(1);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 9, 28));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Thursday September 29

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 9, 29));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);
        wh.setTime(new BigDecimal(15.30));

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 9, 29));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Friday September 30

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 30));
        wh.setWeight(new BigDecimal(115));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 9, 30));
        wh.setWeight(new BigDecimal(135));
        wh.setReps(2);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        // Saturday October 1st

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 1));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.30));

        wh = workoutHistoryDAO.insert(wh);

        // Monday October 3rd

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 10, 3));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 10, 3));
        wh.setReps(3);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 10, 3));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Tuesday October 4th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 10, 4));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 4));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(10.45));

        wh = workoutHistoryDAO.insert(wh);

        // Wednesday October 5th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 10, 5));
        wh.setReps(20);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 10, 5));
        wh.setReps(15);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 10, 5));
        wh.setReps(3);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 10, 5));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Thursday October 6th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 10, 6));
        wh.setWeight(new BigDecimal(155.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 6));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(10.45));

        wh = workoutHistoryDAO.insert(wh);

        // Friday October 7th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 10, 7));
        wh.setWeight(new BigDecimal(115));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 10, 7));
        wh.setWeight(new BigDecimal(135));
        wh.setReps(2);
        wh.setSets(1);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 10, 7));
        wh.setWeight(new BigDecimal(25));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 10, 7));
        wh.setWeight(new BigDecimal(35));
        wh.setReps(6);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e8);
        wh.setWorkout_date(LocalDate.of(2016, 10, 7));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Saturday October 8

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 8));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(11.45));

        wh = workoutHistoryDAO.insert(wh);

        // Monday October 10

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 10, 10));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 10, 10));
        wh.setReps(3);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 10, 10));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Tuesday October 4th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 10, 10));
        wh.setWeight(new BigDecimal(135.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 10));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(10.45));

        wh = workoutHistoryDAO.insert(wh);

        // Wednesday October 11th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 10, 11));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 10, 11));
        wh.setReps(5);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 10, 11));
        wh.setReps(40);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Thursday October 12th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 10, 12));
        wh.setWeight(new BigDecimal(165.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 12));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(10.45));

        wh = workoutHistoryDAO.insert(wh);

        // Friday October 13th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 10, 13));
        wh.setWeight(new BigDecimal(115));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e6);
        wh.setWorkout_date(LocalDate.of(2016, 10, 13));
        wh.setWeight(new BigDecimal(135));
        wh.setReps(4);
        wh.setSets(2);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 10, 13));
        wh.setWeight(new BigDecimal(25));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e7);
        wh.setWorkout_date(LocalDate.of(2016, 10, 13));
        wh.setWeight(new BigDecimal(35));
        wh.setReps(6);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e8);
        wh.setWorkout_date(LocalDate.of(2016, 10, 13));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Saturday October 14

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 14));
        wh.setDistance(new BigDecimal(2.0));
        wh.setTime(new BigDecimal(14.15));

        wh = workoutHistoryDAO.insert(wh);

        // Monday October 16

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 10, 16));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 10, 16));
        wh.setReps(3);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 10, 16));
        wh.setReps(25);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        // Tuesday October 17th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e4);
        wh.setWorkout_date(LocalDate.of(2016, 10, 17));
        wh.setWeight(new BigDecimal(165.0));
        wh.setReps(10);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e5);
        wh.setWorkout_date(LocalDate.of(2016, 10, 17));
        wh.setDistance(new BigDecimal(1.5));
        wh.setTime(new BigDecimal(10.45));

        wh = workoutHistoryDAO.insert(wh);

        // Wednesday October 18th

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e);
        wh.setWorkout_date(LocalDate.of(2016, 10, 18));
        wh.setReps(20);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e2);
        wh.setWorkout_date(LocalDate.of(2016, 10, 18));
        wh.setReps(5);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);

        wh = new WorkoutHistory();
        wh.setAccount(a);
        wh.setExercise(e3);
        wh.setWorkout_date(LocalDate.of(2016, 10, 18));
        wh.setReps(40);
        wh.setSets(3);

        wh = workoutHistoryDAO.insert(wh);
        
    }

}
