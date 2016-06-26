package com.bafoly.tutorials.roles.config.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.bafoly.tutorials.roles.model.Movie;

public class MovieFieldSetMapper implements FieldSetMapper<Movie>{

	@Override
	public Movie mapFieldSet(FieldSet fieldSet) throws BindException {
		
		Movie mov = new Movie();
		
		String year = fieldSet.readString("year");
		String length = fieldSet.readString("length");
		String title = fieldSet.readString("title");
		String subject = fieldSet.readString("subject");
		String actor = fieldSet.readString("actor");
		String actress = fieldSet.readString("actress");
		String director = fieldSet.readString("director");

		mov.setActor(actor);
		mov.setActress(actress);
		mov.setDirector(director);
		mov.setSubject(subject);
		mov.setTitle(title);
		
		if(!year.isEmpty())
			mov.setYear(Integer.valueOf(year));

		if(!length.isEmpty())
			mov.setLength(Integer.valueOf(length));
		
		return mov;
	}

	
	

}
