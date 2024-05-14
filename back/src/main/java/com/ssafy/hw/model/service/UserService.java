package com.ssafy.hw.model.service;

import java.util.List;

import com.ssafy.hw.model.dto.User;

public interface UserService {

	int insert(User user);
	
	User searchById(String id);
	
	List<User> selectAll();
}
