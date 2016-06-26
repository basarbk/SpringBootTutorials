package com.bafoly.tutorials.roles.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.bafoly.tutorials.roles.dao.UserDao;
import com.bafoly.tutorials.roles.model.TutorialUser;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	UserDao userDao;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		TutorialUser u1 = new TutorialUser();
		u1.setName("admin");
		u1.setEmail("admin");
		u1.setPassword("admin");
		u1.setAdmin(true);
		
		userDao.save(u1);
		
		
		TutorialUser u2 = new TutorialUser();
		u2.setPassword("user");
		u2.setName("user");
		u2.setEmail("user");
		
		userDao.save(u2);
		
		
	}
	
	

}
