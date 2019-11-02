package org.freedom.boot.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.freedom.boot.bean.BookExample;
import org.freedom.boot.bean.User;
import org.freedom.boot.bean.UserExample;
import org.freedom.boot.bean.BookExample.Criteria;
import org.freedom.boot.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {

	@Autowired
	UserMapper userMapper;

	/**
	 * 根据日期，条件查询用户
	 * @param date
	 * @param condition
	 * @return
	 */
	public List<User> getUserList(Date date, String condition) {
		boolean ifFindByUserId = true;
		UserExample userExample = new UserExample();
		UserExample.Criteria criteriaUserkId = userExample.createCriteria();
		UserExample.Criteria criteriaUserName = userExample.createCriteria();

		if (condition != "") {
			Pattern pattern = Pattern.compile("[0-9]*");
			if (pattern.matcher(condition).matches()) {
				criteriaUserkId.andUserIdEqualTo(Integer.valueOf(condition));
				criteriaUserName.andNickNameLike("%" + condition + "%");
			} else {
				criteriaUserName.andNickNameLike("%" + condition + "%");
				ifFindByUserId = false;
			}

		}

		if (date != null) {
			if (ifFindByUserId) {
				criteriaUserkId.andLoginTimeGreaterThanOrEqualTo(date);
			}
			criteriaUserName.andLoginTimeGreaterThanOrEqualTo(date);
			Date date2 = date;
			Calendar c = Calendar.getInstance();
			c.setTime(date2);
			c.add(Calendar.DATE, 1);
			if (ifFindByUserId) {
				criteriaUserkId.andLoginTimeLessThanOrEqualTo(c.getTime());
			}

			criteriaUserName.andLoginTimeLessThanOrEqualTo(c.getTime());

		}
		userExample.or(criteriaUserkId);
		userExample.or(criteriaUserName);
		userExample.setOrderByClause("login_time DESC");

		return userMapper.selectByExample(userExample);

	}
	
	/**
	 * 根据主键查询用户
	 * @param userId
	 * @return
	 */
	public User getUser(Integer userId)
	{
		return userMapper.selectByPrimaryKey(userId);
	}
	
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	public int updateUser(User user)
	{
		return userMapper.updateByPrimaryKeySelective(user);
	}
	
	/**
	 * 根据主键删除用户
	 * @param userId
	 * @return
	 */
	public int deleteUser(Integer userId)
	{
		return userMapper.deleteByPrimaryKey(userId);
	}

}
