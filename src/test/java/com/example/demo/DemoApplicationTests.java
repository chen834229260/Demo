package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.dao.MenuPermissionDao;
import com.example.dao.UserDao;
import com.example.dao.UserPermissionDao;
import com.example.domain.MenuPermission;
import com.example.domain.Permission;
import com.example.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserPermissionDao permissionDao;
	@Autowired
	private MenuPermissionDao menuDao;
	
	@Test
	public void contextLoads() {
		User user=userDao.findByuserName("admin");
		System.out.println(user);
	}
	@Test
	public void context() {
		List<Permission> list=permissionDao.findByUserName("tester");
		for(Permission p:list){
			System.out.println(p);
		}
	}
	@Test
	public void menucontext() {
		
		List<MenuPermission> listParent = menuDao.getParentId();
		List<MenuPermission> listChild = menuDao.getChildId();
		Map<MenuPermission, List<MenuPermission>> map = new HashMap<MenuPermission, List<MenuPermission>>();
		List<MenuPermission> listChilds = null;
		for (MenuPermission list : listParent) {
			listChilds = new ArrayList<MenuPermission>();
			for (MenuPermission childlist : listChild) {
				if (list.getId() == childlist.getParent_id()) {
					listChilds.add(childlist);
				}
			}
			map.put(list, listChilds);
		}
		Iterator<Map.Entry<MenuPermission, List<MenuPermission>>> entries = map.entrySet().iterator(); 
		while (entries.hasNext()) { 
		  Entry<MenuPermission, List<MenuPermission>> entry = entries.next(); 
		  System.out.println("Key = " + entry.getKey()  ); 
		  System.out.println("Value = " + entry.getValue());
		}
	}
}
