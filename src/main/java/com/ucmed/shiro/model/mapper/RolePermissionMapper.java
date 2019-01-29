package com.ucmed.shiro.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ucmed.shiro.model.bean.pojo.RolePermissionKey;

import java.util.List;

@Mapper
public interface RolePermissionMapper {
    int deleteByPrimaryKey(RolePermissionKey key);
    int insert(RolePermissionKey record);
    int insertSelective(RolePermissionKey record);
	List<RolePermissionKey> findByRole(int roleId);
}