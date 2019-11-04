package org.freedom.boot.bean;

public class Role {
    private Integer role_id;

    private String name;

    private String name_zh;

    public Integer getRoleId() {
        return role_id;
    }

    public void setRoleId(Integer roleId) {
        this.role_id = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNameZh() {
        return name_zh;
    }

    public void setNameZh(String nameZh) {
        this.name_zh = nameZh == null ? null : nameZh.trim();
    }
}