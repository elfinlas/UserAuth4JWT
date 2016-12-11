package com.mhlab.userauth.domain;

import java.util.Date;

public class AccountDataVO {
	private int ta_idx;
	private String ta_id;
	private String ta_pw;
	private Date ta_create_date;
	private String ta_secret_key;
	
	public int getTa_idx() {
		return ta_idx;
	}
	public void setTa_idx(int ta_idx) {
		this.ta_idx = ta_idx;
	}
	public String getTa_id() {
		return ta_id;
	}
	public void setTa_id(String ta_id) {
		this.ta_id = ta_id;
	}
	public String getTa_pw() {
		return ta_pw;
	}
	public void setTa_pw(String ta_pw) {
		this.ta_pw = ta_pw;
	}
	public Date getTa_create_date() {
		return ta_create_date;
	}
	public void setTa_create_date(Date ta_create_date) {
		this.ta_create_date = ta_create_date;
	}
	public String getTa_secret_key() {
		return ta_secret_key;
	}
	public void setTa_secret_key(String ta_secret_key) {
		this.ta_secret_key = ta_secret_key;
	}
}
