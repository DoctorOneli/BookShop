package org.freedom.boot.bean;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AdminInfo {

	private Integer adminId;
	
	@Pattern(regexp="^[A-Za-z0-9]{6,16}$",message="密码由6到16位的英文和数字组成")
	private String password;

	
	private String username;

	private Boolean enabled;

	private Boolean locked;

	private List<AdminRole> adminRole;

	public AdminInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdminInfo(Integer adminId, String password, String username, Boolean enabled, Boolean locked,
			List<AdminRole> adminRole) {
		super();
		this.adminId = adminId;
		this.password = password;
		this.username = username;
		this.enabled = enabled;
		this.locked = locked;
		this.adminRole = adminRole;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public List<AdminRole> getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(List<AdminRole> adminRole) {
		this.adminRole = adminRole;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

}
