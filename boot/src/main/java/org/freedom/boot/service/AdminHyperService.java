package org.freedom.boot.service;

import java.util.ArrayList;
import java.util.List;

import org.freedom.boot.bean.Admin;
import org.freedom.boot.bean.AdminExample;
import org.freedom.boot.bean.AdminExample.Criteria;
import org.freedom.boot.bean.AdminInfo;
import org.freedom.boot.bean.AdminRole;
import org.freedom.boot.bean.AdminRoleExample;
import org.freedom.boot.dao.AdminMapper;
import org.freedom.boot.dao.AdminRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminHyperService {

	@Autowired
	AdminMapper adminMapper;

	@Autowired
	AdminRoleMapper adminRoleMapper;

	/**
	 * 返回所有管理员
	 * 
	 * @return
	 */
	public List<AdminInfo> getAdminRoles(Integer roleId) {
		AdminExample adminExample = new AdminExample();
		if (roleId != 0) {

			Criteria criteria = adminExample.createCriteria();
			criteria.andRoleIdEqualTo(roleId);
			return adminMapper.selectByExample(adminExample);
		}

		return adminMapper.selectByExample(adminExample);

	}

	/**
	 * 更新管理员
	 * 
	 * @param admin
	 * @param roleId
	 * @return
	 */
	public int updateAdmin(Integer adminId, String username, Integer roleId) {
		AdminInfo admin = new AdminInfo();
		admin.setAdminId(adminId);
		admin.setUsername(username);

		if (roleId != 4) {
			// 查询出权限
			AdminRoleExample adminRoleExample = new AdminRoleExample();
			AdminRoleExample.Criteria adminCriteria = adminRoleExample.createCriteria();
			adminCriteria.andAdminIdEqualTo(adminId);
			AdminRole adminRole = adminRoleMapper.selectByExample(adminRoleExample).get(0);

			// 更新权限
			adminRole.setRoleId(roleId);
			adminRoleMapper.updateByPrimaryKeySelective(adminRole);
		}

		return adminMapper.updateByPrimaryKeySelective(admin);

	}

	/**
	 * 重置密码
	 * 
	 * @param adminId
	 * @param password
	 * @return
	 */
	public int updateAdminPwd(Integer adminId, String password) {
		AdminInfo admin = new AdminInfo();
		admin.setAdminId(adminId);

		// BC加密
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		String encodePassword = encoder.encode(password);
		admin.setPassword(encodePassword);

		return adminMapper.updateByPrimaryKeySelective(admin);

	}

	/**
	 * 添加管理员
	 * 
	 * @param username
	 * @param password
	 * @param roleId
	 * @return
	 */
	public int addAdmin(String username, String password, Integer roleId) {
		AdminInfo adminInfo = new AdminInfo();

		if (adminMapper.selectByUserName(username) != null) {
			return -1;
		}

		adminInfo.setUsername(username);

		// BC加密
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		String encodePassword = encoder.encode(password);
		adminInfo.setPassword(encodePassword);
		adminInfo.setLocked(false);
		adminInfo.setEnabled(true);

		if (adminMapper.insertSelective(adminInfo) == 1) {
			Integer adminId = adminInfo.getAdminId();
			AdminRole adminRole = new AdminRole();
			adminRole.setAdminId(adminId);
			adminRole.setRoleId(roleId);
			return adminRoleMapper.insertSelective(adminRole);
		}

		else {
			return 0;
		}

	}

	/**
	 * 根据主键删除管理员
	 * 
	 * @param id
	 * @return
	 */
	public int deleteAdmin(Integer id) {
		return adminMapper.deleteByPrimaryKey(id);
	}

}
