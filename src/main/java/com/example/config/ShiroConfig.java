package com.example.config;

import java.util.LinkedHashMap;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class ShiroConfig {

	/**
	 * 
	 * anon: 匿名拦截器，即不需要登录即可访问；一般用于静态资源过滤；示例/static/**=anon
	 * 
	 * authc: 基于表单的拦截器；如/**=authc，如果没有登录会跳到相应的登录页面登录
	 * 
	 * logout: 退出拦截器，主要属性：redirectUrl：退出成功后重定向的地址（/），示例/logout=logout
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 设置securityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 登陆的url
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登陆成功后跳转的url
		shiroFilterFactoryBean.setSuccessUrl("/index");
		// 未授权url
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

		// 定义filterChain,静态资源不拦截
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/assets/**", "anon");
		// druid数据源监控页面不拦截
		filterChainDefinitionMap.put("/druid/**", "anon");
		// 配置退出过滤器,其中具体的退出代码shiro实现
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/", "anon");
		// 除上以外所有url都必须认证通过才可以访问,为通过认证自动访问loginurl
		// user指的是用户认证通过或者配置了Remember Me记住用户登录状态后可访问。
		filterChainDefinitionMap.put("/**", "user");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public SecurityManager securityManager() {
		// 配置securityManager,并注入shiroRealm
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(shiroRealm());

		defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
		return defaultWebSecurityManager;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		// shiro生命周期处理器
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public ShiroRealm shiroRealm() {
		ShiroRealm shiroRealm = new ShiroRealm();
		return shiroRealm;
	}

	/**
	 * cookie对象
	 * 
	 * @return
	 */
	public SimpleCookie rememberMeCookie() {
		// 设置cookie名称，对应login.html页面的<input type="checkbox" name="rememberMe"/>
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		// 设置cookie的过期时间，单位为秒，这里为一天
		cookie.setMaxAge(86400);
		return cookie;
	}

	/**
	 * cookie管理对象
	 * 
	 * @return
	 */
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		// rememberMe cookie加密的密钥,必须项,当不设置登陆时会报错
		cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		return cookieRememberMeManager;
	}

	/**
	 * Shiro为我们提供了一些和权限相关的注解，如下所示：
	 * 
	 * // 表示当前Subject已经通过login进行了身份验证；即Subject.isAuthenticated()返回true。
	 * 
	 * @RequiresAuthentication // 表示当前Subject已经身份验证或者通过记住我登录的。
	 * @RequiresUser // 表示当前Subject没有身份验证或通过记住我登录过，即是游客身份。
	 * @RequiresGuest // 表示当前Subject需要角色admin和user。
	 * @RequiresRoles(value={"admin","user"}, logical= Logical.AND) //表示当前Subject需要权限user:a或user:b。
	 * @RequiresPermissions (value={"user:a", "user:b"}, logical= Logical.OR)
	 * 
	 * @return
	 */
	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor" })
	public DefaultAdvisorAutoProxyCreator advisorAuthoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAuthoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAuthoProxyCreator.setProxyTargetClass(true);
		return advisorAuthoProxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}
