package com.mhlab.userauth.persistance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.mhlab.userauth.domain.AccountDataVO;

@Repository
public class AccountDataDAOImpl implements AccountDataDAO {
	private static final String NAMESPACE = "com.mhlab.userauth.AccountDataMapper";
	
	@Inject
	private SqlSession session;
	
	@Override
	public void ta_insert(AccountDataVO accountDataVO) throws Exception{
		session.insert(NAMESPACE+".ta_insert",accountDataVO);
	}
	
	@Override
	public List<AccountDataVO> ta_getByListForAll() throws Exception{
		return session.selectList(NAMESPACE+".ta_getByListForAll");
	}
	
	@Override
	public AccountDataVO ta_getByOneForIdAndPw(String ta_id, String ta_pw) throws Exception{
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ta_id", ta_id);
		paramMap.put("ta_pw", ta_pw);
		return session.selectOne(NAMESPACE+".ta_getByOneForIdAndPw", paramMap);
	}
	
	@Override
	public AccountDataVO ta_getByOneForId(String ta_id) throws Exception{
		return session.selectOne(NAMESPACE+".ta_getByOneForId", ta_id);
	}
	
	@Override
	public AccountDataVO ta_getByOneForPw(String ta_pw) throws Exception{
		return session.selectOne(NAMESPACE+".ta_getByOneForPw", ta_pw);
	}
	
	@Override
	public void ta_updateForIdx(AccountDataVO accountDataVO) throws Exception{
		session.update(NAMESPACE+".ta_updateForIdx",accountDataVO);
	}
	
	@Override
	public void ta_deleteForIndex(int ta_idx) throws Exception{
		session.delete(NAMESPACE+".ta_deleteForIndex", ta_idx);
	}
	
}
