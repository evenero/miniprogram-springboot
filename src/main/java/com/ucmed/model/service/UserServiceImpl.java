package com.ucmed.model.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucmed.model.bean.pojo.User;
import com.ucmed.model.bean.pojo.UserRoleKey;
import com.ucmed.model.bean.vo.UserRolesVO;
import com.ucmed.model.mapper.UserMapper;
import com.ucmed.model.mapper.UserRoleMapper;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(User record) {
		return userMapper.insert(record);
	}

	@Override
	public int insertSelective(User record) {
		return userMapper.insertSelective(record);
	}

	@Override
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(User record) {
		return userMapper.updateByPrimaryKey(record);
	}

	@Override
	public UserRolesVO getUserAndRoles(Integer id) {
		return userMapper.getUserAndRoles(id);
	}

	@Override
	public User findUser(String username, String password) {
		return userMapper.findUser(username, password);
	}

	@Override
	public User findUserByMobile(String mobile) {
		return userMapper.findUserByMobile(mobile);
	}

	@Override
	public User findUserByUserName(String username) {
		return userMapper.findUserByUserName(username);
	}

	@Override
	public String setUser(User user, String roleIds) {
		int userId;
		if (user.getId() != null) {
			// 判断用户是否已经存在
//			User existUser = userMapper.findUserByMobile(user.getMobile());
//			if (null != existUser
//					&& !String.valueOf(existUser.getId()).equals(
//							String.valueOf(user.getId()))) {
//				return "该手机号已经存在";
//			}
			User exist = userMapper.findUserByUserName(user.getUsername());
			if (null != exist && !String.valueOf(exist.getId()).equals(
							String.valueOf(user.getId()))) {
				return "USERNAME_ALREADY_EXIST";
			}
			// 更新用户
			userId = user.getId();
			user.setUpdateTime(new Date());
			userMapper.updateByPrimaryKeySelective(user);
			// 删除之前的角色
			List<UserRoleKey> urs = this.userRoleMapper.findByUserId(userId);
			if (null != urs && urs.size() > 0) {
				for (UserRoleKey ur : urs) {
					userRoleMapper.deleteByPrimaryKey(ur);
				}
			}
		} else {
			// 判断用户是否已经存在
//			User existUser = userMapper.findUserByMobile(user.getMobile());
//			if (null != existUser) {
//				return "该手机号已经存在";
//			}
			User exist = userMapper.findUserByUserName(user.getUsername());
			if (null != exist) {
				return "USERNAME_ALREADY_EXIST";
			}
			// 新增用户
			user.setInsertTime(new Date());
			user.setIsDel(false);
			user.setIsJob(false);
			userMapper.insert(user);
			userId = user.getId();
		}
		// 给用户授角色
		String[] arrays = roleIds.split(",");
		for (String roleId : arrays) {
			UserRoleKey urk = new UserRoleKey();
			urk.setRoleId(Integer.valueOf(roleId));
			urk.setUserId(userId);
			userRoleMapper.insert(urk);
		}
		return "SUCCESS";
	}

	@Override
	public List<User> selectUserByUserNamePagination(String username, Integer pageStart, Integer pageSize) {
		return userMapper.selectUserByUserNamePagination(username, pageStart, pageSize);
	}
	
	@Override
	public int selectUserTotalCountByUserName(String username) {
		return userMapper.selectUserTotalCountByUserName(username);
	}

//	@Override
//	public List<User> getAllData() {
//		return userMapper.getAllData();
//	}
	
}
