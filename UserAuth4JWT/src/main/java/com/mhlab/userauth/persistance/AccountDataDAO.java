package com.mhlab.userauth.persistance;

import java.util.List;

import com.mhlab.userauth.domain.AccountDataVO;

public interface AccountDataDAO {
	public void ta_insert(AccountDataVO accountDataVO) throws Exception;
	public List<AccountDataVO> ta_getByListForAll() throws Exception;
	public AccountDataVO ta_getByOneForIdAndPw(String ta_id, String ta_pw) throws Exception;
	public AccountDataVO ta_getByOneForId(String ta_id) throws Exception;
	public AccountDataVO ta_getByOneForPw(String ta_pw) throws Exception;
	public void ta_updateForIdx(AccountDataVO accountDataVO) throws Exception;
	public void ta_deleteForIndex(int ta_idx) throws Exception;
}
