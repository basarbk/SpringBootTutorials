package com.bafoly.tutorials.roles.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bafoly.tutorials.roles.config.AuthenticatedUser;
import com.bafoly.tutorials.roles.dao.MovieDao;
import com.bafoly.tutorials.roles.model.Movie;
import com.bafoly.tutorials.roles.model.TutorialUser;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/movies")
public class MovieController {
	
	@Autowired
	MovieDao movieDao;
	
	final String raw = "id,year,length,title,subject,actor,actress,director";
	
	List<String> limited = new ArrayList<String>(Arrays.asList(new String[]{"year", "title", "subject"}));
	
	List<String> all = new ArrayList<String>(Arrays.asList(raw.split(",")));
	
	@RequestMapping(method=RequestMethod.GET, 
					produces=MediaType.APPLICATION_JSON_VALUE)
	public MappingJacksonValue getMovies(
			@AuthenticatedUser TutorialUser user, 
			@RequestParam(value = "fields", defaultValue = raw) String rawFields,
			@RequestParam(value="page", defaultValue="0") int pageValue,
			@RequestParam(value="count", defaultValue="10") int countValue){
		
		Page<Movie> m = movieDao.findAll(new PageRequest(pageValue, countValue));
		return getMappingJacksonValue(rawFields, user, m.getContent());
	}
	
	
	@RequestMapping(value={"/{id:[0-9]+}"}, method=RequestMethod.GET)
	public MappingJacksonValue getMovie(
			@AuthenticatedUser TutorialUser user, @PathVariable("id") long id, 
			@RequestParam(value = "fields", defaultValue = raw) String rawFields){
		
		Movie m = movieDao.findOne(id);

		return getMappingJacksonValue(rawFields, user, m);
	}
	
	private MappingJacksonValue getMappingJacksonValue(String rawFields, TutorialUser user, Object object){
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(object);
		
		List<String> incomingFields = new ArrayList<String>(Arrays.asList(rawFields.split(",")));
		
		if(user == null || (user!=null && !user.isAdmin())){
			incomingFields.retainAll(limited);
		} else {
			incomingFields.retainAll(all);
		}
		
		if(incomingFields.size()==0)
			throw new IllegalArgumentException("Not valid fields: "+rawFields);
		
		String[] finalFields = new String[incomingFields.size()];
		
		for(int i = 0 ; i<incomingFields.size();i++)
			finalFields[i] = incomingFields.get(i);
		
		mappingJacksonValue.setFilters(new SimpleFilterProvider().addFilter("movie", SimpleBeanPropertyFilter.filterOutAllExcept(finalFields)));
		return mappingJacksonValue;
	}

}
