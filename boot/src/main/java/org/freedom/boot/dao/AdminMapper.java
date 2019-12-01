package org.freedom.boot.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.freedom.boot.bean.Admin;
import org.freedom.boot.bean.AdminExample;
import org.freedom.boot.bean.AdminInfo;
import org.freedom.boot.bean.AdminRole;
import org.freedom.boot.bean.Role;

public interface AdminMapper {
    
    Admin selectByUserName(String username);	
    
    List<Role> selectAdminRolesByAdminId(Integer id);
    
    List<AdminInfo> selectByExample(AdminExample example);
    
    int updateByPrimaryKeySelective(AdminInfo record);
    
    int insertSelective(AdminInfo record);
    
    int deleteByPrimaryKey(Integer adminId);
 
}