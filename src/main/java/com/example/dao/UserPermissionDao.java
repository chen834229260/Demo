package com.example.dao;

import java.util.List;

import com.example.domain.Permission;

public interface UserPermissionDao {
	
	List<Permission> findByUserName(String username);
	
	List<Permission> getParentid(Integer id);
	
	List<Permission> getChildId(Integer id);
}
