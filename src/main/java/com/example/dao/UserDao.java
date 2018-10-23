package com.example.dao;

import com.example.domain.User;

public interface UserDao {
	User findByuserName(String username);
}
