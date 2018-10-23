package com.example.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.dao.UserDao;
import com.example.dao.UserPermissionDao;
import com.example.dao.UserRoleDao;
import com.example.domain.Permission;
import com.example.domain.Role;
import com.example.domain.User;

public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private UserPermissionDao userPermissionDao;

	/**
	 * 获取用户角色和权限
	 * 我们通过方法userRoleMapper.findByUserName(userName)
	 * 和userPermissionMapper.findByUserName(userName)获取了当前登录用户的角色和权限集，
	 * 然后保存到SimpleAuthorizationInfo对象中，并返回给Shiro，这样Shiro中就存储了当前用户的角色和权限信息了。
	 * 
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		String username = user.getUsername();

		System.out.println("用户" + username + "获取权限-----ShiroRealm.doGetAuthorizationInfo");
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		
		// 获取用户角色集
		List<Role> roleList = userRoleDao.findByUserName(username);
		Set<String> roleSet=new HashSet<String>();
		for(Role r:roleList){
			roleSet.add(r.getName());
		}
		simpleAuthorizationInfo.setRoles(roleSet);
		
		//获取用户权限集
		List<Permission> permissionList=userPermissionDao.findByUserName(username);
		Set<String> permissionSet=new HashSet<String>();
		for(Permission p:permissionList){
			permissionSet.add(p.getName());
		}
		simpleAuthorizationInfo.setStringPermissions(permissionSet);
		return simpleAuthorizationInfo;
	}
	
	/**
	 * 登陆认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());

		System.out.println("用户" + userName + "认证-----ShiroRealm.doGetAuthenticationInfo");

		// 通过同hum到数据库查询用户信息

		User user = userDao.findByuserName(userName);
		System.out.println(user);
		// 虽然我们可以准确的获取异常信息，并根据这些信息给用户提示具体错误
		// 但最安全的做法是在登录失败时仅向用户显示通用错误提示信息，例如“用户名或密码错误”。这样可以防止数据库被恶意扫描。
		if (user == null) {
			throw new UnknownAccountException("用户名或密码错误！");
		}
		if (!password.equals(user.getPasswd())) {
			throw new IncorrectCredentialsException("用户名或密码错误！");
		}
		if (user.getStatus().equals("0")) {
			throw new LockedAccountException("账号已被锁定,请联系管理员！");
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
		return info;
	}

}
