package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.entity.UserInfo;
import com.user.repository.UserInfoRepository;

@Service
public class UserService {
	
	@Autowired
	UserInfoRepository userInfoRepository;
	 @Autowired
	    private PasswordEncoder passwordEncoder;
	 public String addUser(UserInfo userInfo) {
	        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
	        userInfoRepository.save(userInfo);
	        return "user added to system ";
	    }
}
