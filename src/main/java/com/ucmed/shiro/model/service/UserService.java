package com.ucmed.shiro.model.service;

import com.ucmed.shiro.model.bean.pojo.User;
import com.ucmed.shiro.model.bean.vo.UserRolesVO;

public interface UserService {
	/**
	 * 分页查询用户列表
	 * @param page
	 * @param limit
	 * @return
	 */
//	PageDataResult getUsers(UserSearchDTO userSearch, int page, int limit);

	/**
	 *	设置用户【新增或更新】
	 * @param user
	 * @param roleIds
	 * @return
	 */
	String setUser(User user, String roleIds);

	/**
	 * 删除用户
	 * @param id
	 * @param isDel
	 * @return
	 */
	int setDelUser(Integer id, Integer insertUid);

	/**
	 * 查询用户数据
	 * @param id
	 * @return
	 */
	UserRolesVO getUserAndRoles(Integer id);

	/**
	 * 根据手机号查询用户数据
	 * @param mobile
	 * @return
	 */
	User findUserByMobile(String mobile);
	
	/**
	 * 根据username查询用户数据
	 * @param mobile
	 * @return
	 */
	User findUserByUserName(String username);
}
