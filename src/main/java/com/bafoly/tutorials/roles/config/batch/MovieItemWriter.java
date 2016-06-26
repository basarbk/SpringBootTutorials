package com.bafoly.tutorials.roles.config.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.bafoly.tutorials.roles.dao.MovieDao;
import com.bafoly.tutorials.roles.model.Movie;

public class MovieItemWriter implements ItemWriter<Movie>{
	
	@Autowired
	MovieDao movieDao;

	@Override
	public void write(List<? extends Movie> items) throws Exception {
		for(Movie m : items)
			movieDao.save(m);
		
	}
	
	

}
