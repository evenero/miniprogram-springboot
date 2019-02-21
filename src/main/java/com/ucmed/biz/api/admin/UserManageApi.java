package com.ucmed.biz.api.admin;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucmed.biz.api.Api;
import com.ucmed.model.bean.dto.UserDTO;
import com.ucmed.model.bean.pojo.Role;
import com.ucmed.model.bean.pojo.User;
import com.ucmed.model.bean.vo.PermissionVO;
import com.ucmed.model.service.UserService;
import com.ucmed.shiro.model.service.AuthService;

import net.sf.json.JSONObject;

/**
 * 用户管理api
 * @author ucmed
 *
 */
@Component
public class UserManageApi implements Api{
	private static final Logger LOG = LoggerFactory.getLogger(UserManageApi.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private AuthService authService;
	/**
	 * 初始化gson
	 */
	private static Gson GSON = new GsonBuilder()
			.serializeNulls() //当字段值为空或null时，依然对该字段进行转换
			.setDateFormat("yyyy-MM-dd HH:mm:ss") //时间转化为特定格式
			.disableHtmlEscaping() //防止特殊字符出现乱码
			.create();

	@Override
	public JSONObject execute(JSONObject params) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String type = params.optString("type","list");
			String username = params.optString("username","");
			if("list".equals(type)) { //清单
				int totalCount = userService.selectUserTotalCountByUserName(username);
				List<UserDTO> list = this.getUserListPagination(params);
				res.put("list", list);
				res.put("totalCount", totalCount);
				code = 0;
				info = "查询成功";
			}else if("edit".equals(type)){
//				Integer id = params.optInt("id");
//				String value = params.optString("value");
//				String note = params.optString("note");
//				ProgramConfig record = new ProgramConfig();
//				record.setId(id);
//				if(null!=value&&!"".equals(value.trim())) {
//					record.setConfigValue(value);
//				}
//				if(null!=note&&!"".equals(note.trim())) {
//					record.setNote(note);
//				}
//				record.setUpdateTime(new Date());
//				int i = userService.updateByPrimaryKeySelective(record);
//				if(i>0) {
//					code = 0;
//					info = "数据更新成功";
//				}else {
//					info = "数据更新失败";
//				}
			}else if("search".equals(type)) { //获取用户信息(单个)
				UserDTO user = this.getUserInfoByUserName(params);
				if(user==null) {
					info = "无用户信息！";
				}else {
					
					res.put("user", GSON.toJson(user));
					code = 0;
					info = "查询成功";
				}
			}
		} catch (Exception e) {
			LOG.info("",e);
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res;
	}
	
	
	/**
	 * 分页获取用户清单
	 * @param params
	 * @return
	 */
	private List<UserDTO> getUserListPagination(JSONObject params){
		String username = params.optString("username","");
		int pageIndex = params.optInt("pageIndex",1);
		int pageSize = params.optInt("pageSize",10);
		Integer pageStart = (pageIndex-1)*pageSize;
		List<User> userList = userService.selectUserByUserNamePagination(username, pageStart, pageSize);
		List<UserDTO> list = new ArrayList<UserDTO>();
		for(int i=0;i<userList.size();i++) {
			User u = userList.get(i);
			List<Role> roles = authService.getRoleByUser(u.getId());
			List<PermissionVO> perms = authService.getUserPerms(u.getId());
			UserDTO temp = new UserDTO();
			temp.setUserinfo(u);
			temp.setRoles(roles);
			temp.setPermissions(perms);
			list.add(temp);
		}
		return list;
	}
	private UserDTO getUserInfoByUserName(JSONObject params) {
		String username = params.optString("username","");
		UserDTO temp = new UserDTO();
		List<User> user = userService.selectUserByUserNamePagination(username, 0, 1);
		if(user==null || user.size()<=0 || user.isEmpty()) {
			return null;
		}
		List<Role> roles = authService.getRoleByUser(user.get(0).getId());
		List<PermissionVO> perms = authService.getUserPerms(user.get(0).getId());
		temp.setUserinfo(user.get(0));
		temp.setRoles(roles);
		temp.setPermissions(perms);
		return temp;
	}
}
