package com.mhlab.userauth.service;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.mhlab.userauth.persistance.AccountDataDAO;
import com.mhlab.userauth.util.SupportUtil;

@Service
public class AccountServiceImpl implements AccountService {

	@Inject
	private AccountDataDAO accountDAO;
	
	
	@Override
	public boolean checkLogin(String inputId, String inputPw) {
		boolean isSuccess =false;
		try {if(accountDAO.ta_getByOneForIdAndPw(inputId, inputPw)!=null){isSuccess=true;}}
		catch(Exception e){
			System.out.println("[registerAccount] : " + e.getMessage());
		}	
		System.out.println("checkLogin - inputId = " + inputId);
		System.out.println("checkLogin - inputPw = " + inputPw);
		return isSuccess;
	}
	
	@Override
	public String registerAccount(Map<String, Object> regAccData) {
		String resultMsg="";
		
		String inputId = (String)regAccData.get("inputId");
		String inputPw = (String)regAccData.get("inputPw");
		
		try {
			if(isExistId(inputId)) {resultMsg = "existid";}
			else {
				resultMsg = "success";
				accountDAO.ta_insert(SupportUtil.makeAccountDataVO(inputId, inputPw));
			}
		}
		catch (Exception e) {
			resultMsg = "fail";
			System.out.println("[registerAccount] : " + e.getMessage());
		}
		
		return resultMsg;
	}
	
	
	
	
	
	
	
	/**
	 * 
	 * @param ta_id
	 * @return
	 */
	private boolean isExistId(String ta_id){
		boolean isExist = false;
		
		try {if(accountDAO.ta_getByOneForId(ta_id)!=null){isExist=true;}}
		catch(Exception e) {
			System.out.println("[isExistId] : " + e.getMessage());
		}
		
		return isExist;
	}
	
	
}
