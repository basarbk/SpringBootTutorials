package com.bafoly.tutorials.roles.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bafoly.tutorials.roles.dao.UserDao;
import com.bafoly.tutorials.roles.model.TutorialUser;

@Service
public class UserAuthService implements UserDetailsService{

	@Autowired
	UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		TutorialUser user = userDao.findByemail(email);
		if(user == null)
			throw new UsernameNotFoundException("Could not find user " + email);
		
		return user;
	}
	
	

}
