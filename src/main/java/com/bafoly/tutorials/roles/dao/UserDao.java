package com.bafoly.tutorials.roles.dao;

import org.springframework.data.repository.CrudRepository;

import com.bafoly.tutorials.roles.model.TutorialUser;

public interface UserDao extends CrudRepository<TutorialUser, Long>{
	
	TutorialUser findByemail(String email);

}
