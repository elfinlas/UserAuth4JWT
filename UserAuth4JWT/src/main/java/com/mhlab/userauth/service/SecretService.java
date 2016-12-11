package com.mhlab.userauth.service;

import java.util.Map;

public interface SecretService {

	
	public String createToken(String tokenUserId);
	public String validToken(String tokenStr, String userId);
	public Map<String, Object> getTokenPayload(String tokenStr);
}
