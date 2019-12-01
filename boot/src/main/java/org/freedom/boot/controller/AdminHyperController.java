package org.freedom.boot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.freedom.boot.bean.AdminInfo;
import org.freedom.boot.bean.Book;
import org.freedom.boot.bean.BookWithBLOBs;
import org.freedom.boot.bean.Msg;
import org.freedom.boot.service.AdminHyperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/adminhyper")
public class AdminHyperController {
	
	@Autowired
	AdminHyperService adminHyperService;
	
	/**
	 * 是否超级管理员
	 * @return
	 */
	@GetMapping("/hyper")
	public Msg ifHyperAdmin()
	{
		return Msg.success();
	}
	
	/**
	 * 返回(根据角色id)所有管理员
	 * @return
	 */
	@GetMapping("/role")
	public Msg getAdminRoles(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
			@RequestParam("role_id") Integer roleId)
	{
		PageHelper.startPage(pageIndex, 10);
		PageInfo pageInfo = new PageInfo(adminHyperService.getAdminRoles(roleId));
		return Msg.success().add("role", pageInfo);
	 
	}
	
	/**
	 * 更新管理员
	 * @param username
	 * @param adminId
	 * @param roleId
	 * @return
	 */
	@PutMapping("/role")
	public Msg updateAdmin(@Pattern(regexp="(^[A-Za-z0-9]{2,12}$)",message="用户名可含有英文、数字,长度为2到12位") String username ,Integer adminId, Integer roleId) {
		
		if (adminHyperService.updateAdmin(adminId,username,roleId) == 1) {
			return Msg.success().add("result", "更新成功");
		} else {
			return Msg.fail().add("result", "更新失败");
		}
	}
	
	/**
	 * 添加管理员
	 * @param username
	 * @param password
	 * @param roleId
	 * @return
	 */
	@PostMapping("/role")
	public Msg addAdmin(@Pattern(regexp="(^[A-Za-z0-9]{2,12}$)",message="用户名可含有英文、数字,长度为2到12位") String username ,@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{6,16}$",message="必须包含一个大写，一个小写字母，一个数字，长度为6到16位") String password , Integer roleId) {
		
		if (adminHyperService.addAdmin(username,password,roleId) == 1) {
			return Msg.success().add("result", "更新成功");
		} else {
			return Msg.fail().add("result", "更新失败");
		}
	}
	
	/**
	 * 重置密码
	 * @param password
	 * @param adminId
	 * @return
	 */
	@PutMapping("/updatepwd")
	public Msg updateAdmin(String password ,Integer adminId) {
		
		if (adminHyperService.updateAdminPwd(adminId, password)== 1) {
			return Msg.success().add("result", "更新成功");
		} else {
			return Msg.fail().add("result", "更新失败");
		}
	}
	
	/**
	 * 根据主键删除管理员
	 * 
	 * @param bookId
	 * @return
	 */
	@DeleteMapping("/role/{id}")
	public Msg deleteAdmin(@PathVariable("id") Integer id) {
		if (adminHyperService.deleteAdmin(id) == 1) {
			return Msg.success().add("result", "删除成功");
		} else {
			return Msg.fail().add("result", "删除失败");
		}
	}
	
}
