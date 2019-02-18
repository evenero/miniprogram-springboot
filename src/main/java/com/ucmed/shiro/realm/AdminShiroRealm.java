package com.ucmed.shiro.realm;
 
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ucmed.model.bean.pojo.Permission;
import com.ucmed.model.bean.pojo.Role;
import com.ucmed.model.bean.pojo.User;
import com.ucmed.model.service.UserService;
import com.ucmed.shiro.config.ShiroConfig;
import com.ucmed.shiro.model.service.AuthService;
import com.ucmed.shiro.utils.ShiroUtils;

import java.util.List;
 
/**
 * Created by ucmed.
 * 自定义权限匹配和账号密码匹配
 */
public class AdminShiroRealm extends AuthorizingRealm {
	private static final Logger LOG = LoggerFactory.getLogger(ShiroConfig.class);
    @Autowired
	private UserService userService;
	@Autowired
	private AuthService authService;
 
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	//授权
    	LOG.info("进入shiro授权实现方法-->MyShiroRealm.doGetAuthorizationInfo()");
    	// 添加权限 和 角色信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取已登录用户信息
  		Object tmp = principals.getPrimaryPrincipal();
  		//因为devtools的热部署容器不同，导致直接强转的话会报错，需代码处理
  		User user = ShiroUtils.convertObjectToBean(tmp, User.class);
        Integer userId = user.getId();
        List<Role> roles = this.authService.getRoleByUser(userId);
        if (null != roles && roles.size() > 0) {
			for (Role role : roles) {
				authorizationInfo.addRole(role.getCode());
				// 角色对应的权限数据
				List<Permission> perms = this.authService.findPermsByRoleId(role.getId());
				if (null != perms && perms.size() > 0) {
					// 授权角色下所有权限
					for (Permission perm : perms) {
						authorizationInfo.addStringPermission(perm.getCode());
					}
				}
			}
		}
        return authorizationInfo;
    }
 
    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
        //UsernamePasswordToken用于存放提交的登录信息
  		UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
  		LOG.info("用户登录认证：验证当前Subject时获取到token为：" + token);
        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User user = userService.findUserByUserName(username);
        System.out.println("----->>userInfo="+user);
        if (user == null) {
        	//用户不存在
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		user, //用户名
        		user.getPassword(), //密码
                Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }
}