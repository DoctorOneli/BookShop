package org.freedom.boot.bean;

public class AdminRole {
    private Integer userRoleId;

    private Integer adminId;

    private Integer roleId;
    
    private Role role;

    
    
    public AdminRole() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    

	public AdminRole(Integer userRoleId, Integer adminId, Integer roleId, Role role) {
		super();
		this.userRoleId = userRoleId;
		this.adminId = adminId;
		this.roleId = roleId;
		this.role = role;
	}



	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}