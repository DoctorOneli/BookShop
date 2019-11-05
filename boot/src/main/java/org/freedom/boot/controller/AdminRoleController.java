package org.freedom.boot.controller;

import org.freedom.boot.bean.Msg;
import org.freedom.boot.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminrole")
public class AdminRoleController {

	@Autowired
	AdminRoleService adminRoleService;
	
	/**
	 * 返回所有管理员
	 * @return
	 */
	
	@GetMapping("/role")
	public Msg getAdminRoles()
	{
		return Msg.success().add("role", adminRoleService.getAdminRoles());
	}
	
}
