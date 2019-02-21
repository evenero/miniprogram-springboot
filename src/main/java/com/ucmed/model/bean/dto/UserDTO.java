package com.ucmed.model.bean.dto;

import java.util.List;

import com.ucmed.model.bean.pojo.Role;
import com.ucmed.model.bean.pojo.User;
import com.ucmed.model.bean.vo.PermissionVO;

public class UserDTO {
	private User userinfo;
	private List<Role> roles;
	private List<PermissionVO> permissions;
	public User getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(User userinfo) {
		this.userinfo = userinfo;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public List<PermissionVO> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<PermissionVO> permissions) {
		this.permissions = permissions;
	}
	@Override
	public String toString() {
		return "UserDTO [userinfo=" + userinfo + ", roles=" + roles + ", permissions=" + permissions + "]";
	}
}
