package com.photobucket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.photobucket.dao.UserDao;
import com.photobucket.model.User;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;

	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return userDao.save(user);
	}

}
