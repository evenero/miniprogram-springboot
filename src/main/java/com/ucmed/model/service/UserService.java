package com.ucmed.model.service;

import org.apache.ibatis.annotations.Param;

import com.ucmed.model.bean.pojo.User;
import com.ucmed.model.bean.vo.UserRolesVO;

public interface UserService {
	int deleteByPrimaryKey(Integer id);
    int insert(User record);
    int insertSelective(User record);
    User selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(User record);
    int updateByPrimaryKey(User record);
    
    /**
	 *	设置用户【新增或更新】
	 * @param user
	 * @param roleIds
	 * @return
	 */
	String setUser(User user, String roleIds);
	/**
	 * 查询用户及对应的角色
	 * @param id
	 * @return
	 */
    UserRolesVO getUserAndRoles(Integer id);
	/**
	 * 根据用户名和密码查找用户
	 * @param username
	 * @param password
	 * @return
	 */
	User findUser(@Param("username") String username,
			@Param("password") String password);
	/**
	 *	根据手机号获取用户数据
	 * @param mobile
	 * @return
	 */
	User findUserByMobile(String mobile);
	/**
	 * 根据用户名获取用户数据
	 * @param username
	 * @return
	 */
	User findUserByUserName(String username);
}
