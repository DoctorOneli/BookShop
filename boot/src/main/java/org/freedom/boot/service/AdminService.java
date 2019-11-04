package org.freedom.boot.service;

import java.util.ArrayList;
import java.util.List;

import org.freedom.boot.bean.Admin;
import org.freedom.boot.bean.AdminExample;
import org.freedom.boot.bean.AdminRole;
import org.freedom.boot.bean.AdminRoleExample;
import org.freedom.boot.bean.Role;
import org.freedom.boot.dao.AdminMapper;
import org.freedom.boot.dao.AdminRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements UserDetailsService {

	@Autowired
	AdminMapper adminMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 
		Admin admin=adminMapper.selectByUserName(username);
		if(admin==null)
		{
			throw new UsernameNotFoundException("账户不存在");
		}
		
		 
		 admin.setRoles(adminMapper.selectAdminRolesByAdminId(admin.getAdminId()));
		
		return admin;
		 
	}

}
