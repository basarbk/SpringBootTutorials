package com.bafoly.tutorials.roles.config.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.bafoly.tutorials.roles.model.Movie;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
	
	// tag::jobstep[]
    @Bean
    public Job importMovieJob() {
        return jobBuilderFactory.get("importMovieJob")
                .incrementer(new RunIdIncrementer())
                .flow(steps())
                .end()
                .build();
    }

    @Bean
    public Step steps() {
        return stepBuilderFactory.get("steps")
                .<Movie, Movie> chunk(10)
                .reader(reader())
                .writer(writer())
                .build();
    }
	// end::jobstep[]
	
    
    @Bean
    public ItemWriter<Movie> writer(){
    	return new MovieItemWriter();
    }
    
    @Bean
    public FlatFileItemReader<Movie> reader() {
        FlatFileItemReader<Movie> reader = new FlatFileItemReader<Movie>();
        reader.setResource(new ClassPathResource("movies.csv"));
        reader.setLinesToSkip(2);
        reader.setLineMapper(lineMapper());
        return reader;
    }
    
	@Bean
	public LineMapper<Movie> lineMapper() {
		DefaultLineMapper<Movie> lineMapper = new DefaultLineMapper<Movie>();
		
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(";");
		lineTokenizer.setNames(new String[]{ "year", "length", "title", "subject", "actor", "actress", "director","popularity","awards","image"});
		
		BeanWrapperFieldSetMapper<Movie> fieldSetMapper = new BeanWrapperFieldSetMapper<Movie>();
		fieldSetMapper.setTargetType(Movie.class);
		
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(new MovieFieldSetMapper());
		
		return lineMapper;
	}

}
