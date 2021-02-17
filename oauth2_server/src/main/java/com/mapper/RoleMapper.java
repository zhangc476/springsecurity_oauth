package com.mapper;

import com.domain.SysRole;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface RoleMapper extends Mapper<SysRole> {

    @Select("select r.id, r.role_name roleName, r.role_desc roleDesc " +
            "from sys_role r, sys_user_role ur " +
            "where r.id = ur.rid AND ur.uid=#{id}")
    public List<SysRole> findByUid(Integer id);
}
