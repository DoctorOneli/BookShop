package org.freedom.boot.controller;

import org.freedom.boot.bean.Msg;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admincommon")
public class AdminCommonController {

	/**
	 * 获取当前用户信息
	 * @return
	 */
	@GetMapping("/info")
	public Msg getAdminInfo()
	{
		return Msg.success().add("data", SecurityContextHolder.getContext().getAuthentication()); 
	}

	
}
