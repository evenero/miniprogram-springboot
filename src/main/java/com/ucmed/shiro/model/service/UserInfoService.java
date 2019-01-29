package com.ucmed.shiro.model.service;

import com.ucmed.shiro.model.bean.UserInfo;

public interface UserInfoService {  
     
    /**通过username查找用户信息;*/  
    public UserInfo findByUsername(String username);  
     
}  