package com.elrich.pizzasalesproject.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.elrich.pizzasalesproject.models.Pizza;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JobBuilder jobBuilder;
	
	@Bean
	public FlatFileItemReader<Pizza> reader(){
		FlatFileItemReader<Pizza> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("pizza.csv"));
		reader.setLineMapper(getLinemapper());
		reader.setLinesToSkip(1);
		
		
		
		return reader;
		
	}

	private LineMapper<Pizza> getLinemapper() {
		// TODO Auto-generated method stub
		DefaultLineMapper<Pizza> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[] { "id","pizza_id", "pizza_type_id", "size", "price"});
		lineTokenizer.setIncludedFields(new int[] {0,1,2,3});
		lineMapper.setLineTokenizer(lineTokenizer);
		BeanWrapperFieldSetMapper<Pizza> fieldSetter = new BeanWrapperFieldSetMapper<>();
		fieldSetter.setTargetType(Pizza.class);
		lineMapper.setFieldSetMapper(fieldSetter);
		return lineMapper;
	}
	
	@Bean
	public PizzaItemProcessor processor() {
		return new PizzaItemProcessor();
		
	}
	
	@Bean
	public JdbcBatchItemWriter<Pizza> writer(){
		JdbcBatchItemWriter<Pizza> writer = new JdbcBatchItemWriter<>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Pizza>());
		writer.setSql("insert into pizza(pizzaId, pizzaTypeId,size,price)values(:pizzaId,:pizzaTypeId,:size,:price)");
		writer.setDataSource(dataSource);
		return writer;
	}
	
	@Bean
	public Job importJob() {
		return this.jobBuilder.incrementer(new RunIdIncrementer())
				.flow(step1())
				.end().
				build();
	}

	
	@SuppressWarnings("deprecation")
	@Bean
	private Step step1() {
		// TODO Auto-generated method stub
		return new StepBuilder("sampleStep")
				.<Pizza,Pizza>chunk(5)
				.reader(reader())
				.writer(writer())
				.build();
}
}
