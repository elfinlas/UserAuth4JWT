package com.mhlab.userauth.service;

import java.util.Map;

public interface AccountService {
	
	
	public boolean checkLogin(String inputId, String inputPw);
	public String registerAccount(Map<String, Object> regAccData);
}
