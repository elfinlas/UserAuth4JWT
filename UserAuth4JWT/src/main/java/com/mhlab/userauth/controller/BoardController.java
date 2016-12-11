package com.mhlab.userauth.controller;

import java.util.Date;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mhlab.userauth.service.SecretService;
import com.mhlab.userauth.util.SupportUtil;

@Controller
@RequestMapping(value="/board/*")
public class BoardController {

	@Inject
	private SecretService secretService;
	
	
	
	@RequestMapping(value="main" )
	public String getBoardMain(HttpServletRequest request,  Model model){
		String loginId = (String)request.getSession().getAttribute("loginId");
		String tokenStr = (String)request.getSession().getAttribute("tokenStr");
		
		if(tokenStr!= null && loginId != null){
			String tokenValidMsg = secretService.validToken(tokenStr, loginId);
			
			if(tokenValidMsg.equals("Pass")){
				Map<String, Object> tokenPayload = secretService.getTokenPayload(tokenStr);
				
				model.addAttribute("tokenSub", tokenPayload.get("sub"));
				model.addAttribute("tokenAud", tokenPayload.get("aud"));
				model.addAttribute("tokenJti", tokenPayload.get("jti"));
				model.addAttribute("tokenIss", tokenPayload.get("iss"));
				model.addAttribute("tokenExDate", SupportUtil.getDateObj2String(new Date(Long.valueOf((Integer)tokenPayload.get("exp"))*1000), 2, ""));
			}
			
			model.addAttribute("tokenMsg", tokenValidMsg);
			model.addAttribute("loginId", loginId);
		}
		else{
			model.addAttribute("loginId", "no-login");
		}
		
		
		
		return "/board/main";
	}
	
	
}
