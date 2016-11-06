package com.ssa.ironyard.fitness.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.RedirectView;

import com.ssa.ironyard.fitness.crypto.BCryptSecurePassword;
import com.ssa.ironyard.fitness.model.Account;
import com.ssa.ironyard.fitness.services.FitnessAccountServiceImpl;

@RestController
@RequestMapping(value = "/fitness")
public class LoginController {

	Logger LOGGER = LogManager.getLogger(LoginController.class);
	final FitnessAccountServiceImpl accService;

	@Autowired
	    public LoginController(FitnessAccountServiceImpl s)
	    {
	        this.accService = s;
	    }

	@RequestMapping(value = "")
	public View homeView(HttpSession session) {
		session.invalidate();
		return new InternalResourceView("/login.html");
	}

	@RequestMapping(produces = "application/json", value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getAccount(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<>();

		Account acc = accService.readAccount(request.getParameter("username"));

		if (acc == null)
			map.put("error", "Account/password not found");
		else if (!new BCryptSecurePassword().verify(request.getParameter("password"), acc.getPassword()))
			map.put("error", "Account/password not found");
		else {
			map.put("success", acc);
			session.setAttribute("User successfully validated", acc);
		}

		return ResponseEntity.ok().header("Fitness Account", "Account").body(map);
	}

}
