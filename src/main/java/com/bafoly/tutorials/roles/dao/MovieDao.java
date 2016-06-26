package com.bafoly.tutorials.roles.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.bafoly.tutorials.roles.model.Movie;

public interface MovieDao extends CrudRepository<Movie, Long>{
	
	Page<Movie> findAll(Pageable page);

}
