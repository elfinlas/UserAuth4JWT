package com.mhlab.userauth.service;

import java.util.Map;

public interface SecretService {
	/**
	 * 토큰을 생성하는 메서드
	 * @param tokenUserId 토큰 생성을 요청한 사용자 id
	 * @return 토큰값을 반환한다.
	 */
	public String createToken(String tokenUserId);
	
	/**
	 * 전달받은 토큰을 검증하는 메서
	 * @param tokenStr 토큰값
	 * @param userId 토큰을 발급받은 사용자 id
	 * @return 결과를 String으로 반환
	 */
	public String validToken(String tokenStr, String userId);
	
	/**
	 * 토큰의 정보를 뽑아내는 메서드 
	 * @param tokenStr 토큰
	 * @return 토큰의 정보가 담긴 Map 객체를 반환
	 */
	public Map<String, Object> getTokenPayload(String tokenStr);
}
