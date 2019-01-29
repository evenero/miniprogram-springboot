package com.ucmed.shiro.model.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucmed.shiro.model.bean.UserInfo;
import com.ucmed.shiro.model.mapper.UserInfoRepository;  
   
@Service  
public class UserInfoServiceImpl implements UserInfoService{  
     
    @Autowired
    private UserInfoRepository userInfoRepository;  
     
    @Override
    public UserInfo findByUsername(String username) {  
       System.out.println("UserInfoServiceImpl.findByUsername()");  
       return userInfoRepository.findByUsername(username);  
    }  
     
}  