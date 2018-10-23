package com.example.dao;

import java.util.List;

import com.example.domain.MenuPermission;

public interface MenuPermissionDao {
	List<MenuPermission> getParentId();
	
	List<MenuPermission> getChildId();
}
