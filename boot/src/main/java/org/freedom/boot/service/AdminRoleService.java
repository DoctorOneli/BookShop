package org.freedom.boot.service;

import java.util.List;

import org.freedom.boot.bean.AdminRole;
import org.freedom.boot.dao.AdminRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminRoleService {
	
	@Autowired
	AdminRoleMapper adminRoleMapper;
	
	/**
	 * 返回所有管理员
	 * @return
	 */
	public List<AdminRole> getAdminRoles()
	{
		  return adminRoleMapper.selectByExample(null);
	}

}
