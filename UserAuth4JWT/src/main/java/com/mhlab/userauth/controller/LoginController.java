package com.mhlab.userauth.controller;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mhlab.userauth.service.AccountService;
import com.mhlab.userauth.service.SecretService;

@Controller
@RequestMapping(value="/login/*")
public class LoginController {

	
	@Inject
	private AccountService accountService;
	
	@Inject
	private SecretService secretService;
	
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String getLogin() {
		return "/login/login";
	}
	
	
	@RequestMapping(value="signup", method=RequestMethod.POST)
	public ResponseEntity<String> postSignUp(@RequestBody Map<String, Object> jsonMap){
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String>(accountService.registerAccount(jsonMap), HttpStatus.OK);
		return entity;
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public ResponseEntity<String> postLogin(HttpServletRequest request, @RequestBody Map<String, Object> jsonMap){
		ResponseEntity<String> entity = null;
		String resultMsg= "fail";
		if(accountService.checkLogin((String)jsonMap.get("inputId"), (String)jsonMap.get("inputPw"))){
			request.getSession().setAttribute("loginId", (String)jsonMap.get("inputId"));
			request.getSession().setAttribute("tokenStr", secretService.createToken((String)jsonMap.get("inputId")));
			
			resultMsg="success";
		}
		
		System.out.println("resultMsg = " + resultMsg);
		
		entity = new ResponseEntity<String>(resultMsg, HttpStatus.OK);
		return entity;
	}
	
	
	
}
