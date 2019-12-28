package org.freedom.boot.service;

import org.freedom.boot.bean.User;
import org.freedom.boot.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	UserMapper userMapper;
	
	/**
	 * 根据id查询用户详情
	 * @param id
	 * @return
	 */
	public User getUserDetail(Integer id)
	{
		return userMapper.selectByPrimaryKey(id);
	}
	 
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public int addUser(User User)
	{
		 User newUser=User;
		 return userMapper.insertSelective(newUser);
	}
	
	/**
	 * 更新用户
	 * @param User
	 * @return
	 */
	public int updateUser(User User)
	{
		 return userMapper.updateByPrimaryKeySelective(User);
	}

}
