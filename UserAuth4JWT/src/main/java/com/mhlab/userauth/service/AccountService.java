package com.mhlab.userauth.service;

import java.util.Map;

public interface AccountService {
	/**
	 * 로그인을 검증하는 메서드
	 * @param inputId 사용자로부터 입력받은 Id
	 * @param inputPw 사용자로부터 입력받은 Pw
	 * @return true-성공 fail-실패
	 */
	public boolean checkLogin(String inputId, String inputPw);
	
	
	/**
	 * 회원을 등록하는 메서드
	 * @param regAccData 회원정보가 담긴 Map객체 
	 * @return 결과를 문자열로 반환 
	 */
	public String registerAccount(Map<String, Object> regAccData);
}
