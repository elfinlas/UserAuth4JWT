package com.mhlab.userauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/test/*")
public class TestViewController {

	@RequestMapping(value="testview")
	public String getTestView(){
		return "/test/testview";
	}
	
}
