package com.mhlab.userauth.controller;

import java.util.Date;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mhlab.userauth.service.SecretService;
import com.mhlab.userauth.util.SupportUtil;

@Controller
@RequestMapping(value="/board/*")
public class BoardController {

	@Inject
	private SecretService secretService;
	
	/**
	 * 토큰 정보를 가져와서 페이지에서 보여준다. 
	 * @param request HttpServletRequest 객 
	 * @param model Model 객체
	 * @return 화면 페이지 
	 */
	@RequestMapping(value="main", method=RequestMethod.GET)
	public String getBoardMain(HttpServletRequest request,  Model model){
		//세션에서 값을 가져온다.
		String loginId = (String)request.getSession().getAttribute("loginId");
		String tokenStr = (String)request.getSession().getAttribute("tokenStr");
		
		if(tokenStr!= null && loginId != null){
			String tokenValidMsg = secretService.validToken(tokenStr, loginId);
			if(tokenValidMsg.equals("Pass")){ //토큰 검증을 마친 경우에만 토큰 정보를 출력한다.
				Map<String, Object> tokenPayload = secretService.getTokenPayload(tokenStr);
				model.addAttribute("tokenSub", tokenPayload.get("sub"));
				model.addAttribute("tokenAud", tokenPayload.get("aud"));
				model.addAttribute("tokenJti", tokenPayload.get("jti"));
				model.addAttribute("tokenIss", tokenPayload.get("iss"));
				model.addAttribute("tokenExDate", SupportUtil.getDateObj2String(new Date(Long.valueOf((Integer)tokenPayload.get("exp"))*1000), 2, ""));
			}
			model.addAttribute("tokenMsg", tokenValidMsg);
			model.addAttribute("loginId", loginId);
			model.addAttribute("tokenValue", tokenStr);
		}
		else{model.addAttribute("loginId", "no-login");} //로그인 되어 있지 않은 경우
		return "/board/main";
	}
	
}
