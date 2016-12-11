package com.mhlab.userauth.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.mhlab.userauth.domain.AccountDataVO;

public class SupportUtil {

	
	/**
	 * UUID String을 만들어 리턴한다. 
	 * @return String으로 변환된 UUID
	 */
	public static String makeUUID2String() {
		return UUID.randomUUID().toString();
	}
	
	
	/**
	 * SimpleDateFormat을 맞춰서 리턴해주는 메서드
	 * @param optInt getNowTime 메서드에서 사용할 옵션 값 (1~6)
	 * @param sendFormatStr 6번 옵션 시 커스텀 값
	 * @return 변환된 SimpleDateFormat 객체
	 */
	public static SimpleDateFormat getCustomSimpleDateFormat(int optInt, String sendFormatStr) {
		SimpleDateFormat format = null;
		switch (optInt) {
		case 1: { format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE"); }break; //Full Date
		case 2: { format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); }break; //Full Date
		case 3: { format = new SimpleDateFormat("yyyy-MM-dd"); }break; //Y-M-D
		case 4: { format = new SimpleDateFormat("yyyy-MM-dd-EEE"); }break; //Y-M-D-E
		case 5: { format = new SimpleDateFormat("HH:mm:ss"); }break; //H:m:S
		case 6: { format = new SimpleDateFormat(sendFormatStr); }break; //Custom
		default: break;
		}
		return format;
	}
	
	/**
	 * 전달받은 Date 객체를 알맞는 Format으로 변환 후 String 으로 리턴
	 * @param date 변환할 Date 객체 
	 * @param optInt 사용할 옵션 값 (1~6)
	 * @param sendFormatStr 6번 옵션 시 커스텀 값
	 * @return 변환된 String 날짜 값
	 */
	public static String getDateObj2String(Date date, int optInt, String sendFormatStr) {
		SimpleDateFormat format = getCustomSimpleDateFormat(optInt, sendFormatStr);
		return format.format(date);
	}

	
	//////////////////////////////
	//		Make VO Object
	//////////////////////////////
	
	public static AccountDataVO makeAccountDataVO(String ta_id, String ta_pw){
		AccountDataVO accountVo = new AccountDataVO();
		accountVo.setTa_id(ta_id);
		accountVo.setTa_pw(ta_pw);
		accountVo.setTa_secret_key("non use this project");
		return accountVo;
	}
	
}
