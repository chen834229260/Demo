package com.example.dao;

import java.util.List;

import com.example.domain.Role;

public interface UserRoleDao {
	List<Role> findByUserName(String username);
}
