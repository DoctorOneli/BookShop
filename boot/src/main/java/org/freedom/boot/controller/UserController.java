package org.freedom.boot.controller;

import java.util.List;

import org.freedom.boot.bean.Msg;
import org.freedom.boot.bean.User;
import org.freedom.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class UserController {

	@Autowired
	UserService userService;

	/**
	 * 根据用户id查询用户
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/user/{id}")
	public Msg getUserDetail(@PathVariable("id") Integer id) {
		return Msg.success().add("data", userService.getUserDetail(id));
	}

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@PostMapping("/useradd")
	public Msg addUser(@RequestBody User user) {

		if (userService.addUser(user) >= 0) {
			return Msg.success();
		} else {
			return Msg.fail();
		}
	}

	/**
	 * 更新用户
	 * @param User
	 * @return
	 */
	@PostMapping("/userupdate")
	public Msg updateUser(User user) {
		if (userService.updateUser(user) >= 0) {
			return Msg.success();
		} else {
			return Msg.fail();
		}
	}

}
