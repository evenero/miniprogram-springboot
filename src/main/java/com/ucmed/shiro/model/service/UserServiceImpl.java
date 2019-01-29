package com.ucmed.shiro.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ucmed.shiro.model.bean.pojo.User;
import com.ucmed.shiro.model.bean.pojo.UserRoleKey;
import com.ucmed.shiro.model.bean.vo.UserRolesVO;
import com.ucmed.shiro.model.mapper.UserMapper;
import com.ucmed.shiro.model.mapper.UserRoleMapper;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;

//	@Override
//	public PageDataResult getUsers(UserSearchDTO userSearch, int page, int limit) {
//		// 时间处理
//		if (null != userSearch) {
//			if (StringUtils.isNotEmpty(userSearch.getInsertTimeStart())
//					&& StringUtils.isEmpty(userSearch.getInsertTimeEnd())) {
//				userSearch.setInsertTimeEnd(DateUtil.format(new Date()));
//			} else if (StringUtils.isEmpty(userSearch.getInsertTimeStart())
//					&& StringUtils.isNotEmpty(userSearch.getInsertTimeEnd())) {
//				userSearch.setInsertTimeStart(DateUtil.format(new Date()));
//			}
//			if (StringUtils.isNotEmpty(userSearch.getInsertTimeStart())
//					&& StringUtils.isNotEmpty(userSearch.getInsertTimeEnd())) {
//				if (userSearch.getInsertTimeEnd().compareTo(
//						userSearch.getInsertTimeStart()) < 0) {
//					String temp = userSearch.getInsertTimeStart();
//					userSearch
//							.setInsertTimeStart(userSearch.getInsertTimeEnd());
//					userSearch.setInsertTimeEnd(temp);
//				}
//			}
//		}
//		PageDataResult pdr = new PageDataResult();
//		PageHelper.startPage(page, limit);
//		List<UserRoleDTO> urList = userMapper.getUsers(userSearch);
//		// 获取分页查询后的数据
//		PageInfo<UserRoleDTO> pageInfo = new PageInfo<>(urList);
//		// 设置获取到的总记录数total：
//		pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
//		// 将角色名称提取到对应的字段中
//		if (null != urList && urList.size() > 0) {
//			for (UserRoleDTO ur : urList) {
//				List<Role> roles = roleMapper.getRoleByUserId(ur.getId());
//				if (null != roles && roles.size() > 0) {
//					StringBuilder sb = new StringBuilder();
//					for (int i = 0; i < roles.size(); i++) {
//						Role r = roles.get(i);
//						sb.append(r.getRoleName());
//						if (i != (roles.size() - 1)) {
//							sb.append("，");
//						}
//					}
//					ur.setRoleNames(sb.toString());
//				}
//			}
//		}
//		pdr.setList(urList);
//		return pdr;
//	}

	@Override
	public int setDelUser(Integer id, Integer insertUid) {
		User user = this.userMapper.selectByPrimaryKey(id);
		user.setIsDel(true);
		user.setInsertUid(insertUid);
		return this.userMapper.insertSelective(user);
	}

	@Override
	public String setUser(User user, String roleIds) {
		int userId;
		if (user.getId() != null) {
			// 判断用户是否已经存在
			User existUser = this.userMapper.findUserByMobile(user.getMobile());
			if (null != existUser
					&& !String.valueOf(existUser.getId()).equals(
							String.valueOf(user.getId()))) {
				return "该手机号已经存在";
			}
			User exist = this.userMapper.findUserByUserName(user.getUsername());
			if (null != exist
					&& !String.valueOf(exist.getId()).equals(
							String.valueOf(user.getId()))) {
				return "该用户名已经存在";
			}
			// 更新用户
			userId = user.getId();
			user.setUpdateTime(new Date());
			this.userMapper.updateByPrimaryKeySelective(user);
			// 删除之前的角色
			List<UserRoleKey> urs = this.userRoleMapper.findByUserId(userId);
			if (null != urs && urs.size() > 0) {
				for (UserRoleKey ur : urs) {
					this.userRoleMapper.deleteByPrimaryKey(ur);
				}
			}
		} else {
			// 判断用户是否已经存在
			User existUser = this.userMapper.findUserByMobile(user.getMobile());
			if (null != existUser) {
				return "该手机号已经存在";
			}
			User exist = this.userMapper.findUserByUserName(user.getUsername());
			if (null != exist) {
				return "该用户名已经存在";
			}
			// 新增用户
			user.setInsertTime(new Date());
			user.setIsDel(false);
			user.setIsJob(false);
			this.userMapper.insert(user);
			userId = user.getId();
		}
		// 给用户授角色
		String[] arrays = roleIds.split(",");
		for (String roleId : arrays) {
			UserRoleKey urk = new UserRoleKey();
			urk.setRoleId(Integer.valueOf(roleId));
			urk.setUserId(userId);
			this.userRoleMapper.insert(urk);
		}
		return "ok";
	}

	@Override
	public UserRolesVO getUserAndRoles(Integer id) {
		// 获取用户及他对应的roleIds
		return this.userMapper.getUserAndRoles(id);
	}

	@Override
	public User findUserByMobile(String mobile) {
		return this.userMapper.findUserByMobile(mobile);
	}
	
	@Override
	public User findUserByUserName(String username) {
		return this.userMapper.findUserByUserName(username);
	}

}
