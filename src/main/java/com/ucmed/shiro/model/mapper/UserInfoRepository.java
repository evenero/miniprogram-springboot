package com.ucmed.shiro.model.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.ucmed.shiro.model.bean.UserInfo;  
   
/** 
 * UserInfo持久化类; 
 * @author ucmed
 * @version v.0.1 
 */
@Mapper
public interface UserInfoRepository{  
     
    /**通过username查找用户信息;*/
	@Select({
		"select * from user_info where username = #{username,jdbcType=VARCHAR}"
	})
    public UserInfo findByUsername(String username);  
     
}  