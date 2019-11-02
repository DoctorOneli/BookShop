package org.freedom.boot.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.freedom.boot.bean.Msg;
import org.freedom.boot.bean.User;
import org.freedom.boot.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/adminuser")
public class AdminUserController {

	@Autowired
	AdminUserService adminUserService;

	/**
	 * 根据页码，日期，查询条件返回用户列表
	 * 
	 * @param pageIndex
	 * @param date
	 * @param condition
	 * @return
	 */
	@GetMapping("/user")
	public Msg getUserList(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
			@RequestParam("date") String date, @RequestParam("condition") String condition) {
		PageHelper.startPage(pageIndex, 10);
		Date result = null;
		try {
			if (date != "")
				result = DateUtils.parseDate(date, "yyyy-MM-dd");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PageInfo pageInfo = new PageInfo(adminUserService.getUserList(result, condition));
		return Msg.success().add("user", pageInfo);

	}

	/**
	 * 根据主键返回用户
	 * 
	 * @param id
	 * @return
	 */

	@GetMapping("/user/{id}")
	public Msg getUser(@PathVariable("id") Integer id) {
		return Msg.success().add("user", adminUserService.getUser(id));
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @return
	 */
	@PutMapping("/user")
	public Msg updateUser(@RequestBody User user) {
		if (adminUserService.updateUser(user) == 1) {
			return Msg.success().add("result", "更新成功");
		}

		else {
			return Msg.fail();
		}
	}

	/**
	 * 根据主键删除用户
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/user/{id}")
	public Msg deleteUser(@PathVariable("id") Integer id) {
		if (adminUserService.deleteUser(id) == 1) {
			return Msg.success().add("result", "更新成功");
		}

		else {
			return Msg.fail();
		}
	}

}
