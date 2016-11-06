package com.ssa.ironyard.fitness.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.ssa.ironyard.fitness.crypto.BCryptSecurePassword;
import com.ssa.ironyard.fitness.model.Account;
import com.ssa.ironyard.fitness.model.Goal;
import com.ssa.ironyard.fitness.services.FitnessAccountServiceImpl;
import com.ssa.ironyard.fitness.services.FitnessHistoryServiceImpl;

@RestController
@RequestMapping(value = "/fitness/home")
public class FitnessAccountController
{

    Logger LOGGER = LogManager.getLogger(FitnessAccountController.class);
    final FitnessAccountServiceImpl accService;
    final FitnessHistoryServiceImpl historyService;

    @Autowired
    public FitnessAccountController(FitnessAccountServiceImpl s, FitnessHistoryServiceImpl h)
    {
        this.accService = s;
        this.historyService = h;

    }

    @RequestMapping(value = "/{id}")
    public View mainView()
    {
        View main = new InternalResourceView("/index.html");
        return main;
    }

    @RequestMapping(produces = "application/json", value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getAccount(@PathVariable int id, @RequestParam String dummy)
    {
        Map<String, Object> map = new HashMap<>();

        Account acc = accService.readAccount(id);
        Map<String, BigDecimal> statsMap = historyService.getStats(id);
        
        
        if (acc == null)
            map.put("error", "Account not found");
        else{
            map.put("success", acc);
            map.put("stats", statsMap);
        }

        return ResponseEntity.ok().header("Fitness Account", "Account").body(map);
    }

    @RequestMapping(produces = "application/json", value = "/login", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> createAccount(HttpServletRequest request)
    {
        Map<String, Object> map = new HashMap<>();

        Account a = accService.insertAccount(new Account(request.getParameter("username"),
                new BCryptSecurePassword().secureHash(request.getParameter("password"))));

        if (a == null)
            map.put("error", "Account/password not found");
        else
            map.put("success", a);

        return ResponseEntity.ok().header("Fitness Account", "Account").body(map);
    }

    @RequestMapping(produces = "application/json", value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Account>> updateAccount(@PathVariable Integer id, HttpServletRequest request)
    {
        ResponseEntity.status(HttpStatus.CREATED);
        Map<String, Account> map = new HashMap<>();

        Account a = new Account();
        a.setId(id);
        a.setUsername(request.getParameter("username"));
        a.setFirstName(request.getParameter("firstName"));
        a.setLastName(request.getParameter("lastName"));
        a.setAge(Integer.parseInt(request.getParameter("age")));
        a.setGender(Account.Gender.valueOf(request.getParameter("gender")));
        a.setGoal(new Goal(Goal.Type.valueOf(request.getParameter("goal"))));
        a.setHeight(Double.parseDouble(request.getParameter("height")));
        a.setWeight(Double.parseDouble(request.getParameter("weight")));

        Account updatedAccount = accService.updateAccount(a);

        if (updatedAccount == null)
            map.put("error", updatedAccount);
        else
            map.put("success", updatedAccount);

        return ResponseEntity.ok().header("Fitness Account", "Account").body(map);
    }

    @RequestMapping(produces = "application/json", value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, Boolean>> deleteAccount(@PathVariable Integer id)
    {
        Boolean b = false;
        Map<String, Boolean> map = new HashMap<>();

        if (accService.readAccount(id) != null)
            b = accService.deleteAccount(id);

        if (b == false)
            map.put("error", b);
        else
            map.put("success", b);

        return ResponseEntity.ok().header("Fitness Account", "Account").body(map);

    }

}
