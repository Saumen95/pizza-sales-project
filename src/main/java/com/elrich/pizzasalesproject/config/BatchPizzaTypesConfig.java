package com.elrich.pizzasalesproject.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
import org.springframework.core.io.ClassPathResource;
import com.elrich.pizzasalesproject.config.PizzaTypeItemProcessor;
import com.elrich.pizzasalesproject.models.PizzaTypes;

public class BatchPizzaTypesConfig {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JobBuilder jobBuilder;
	
	@Bean
	public FlatFileItemReader<PizzaTypes> reader(){
		FlatFileItemReader<PizzaTypes> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("pizza_types.csv"));
		reader.setLineMapper(getLinemapper());
		reader.setLinesToSkip(1);
		return reader;		
		
	}

	private LineMapper<PizzaTypes> getLinemapper() {
		// TODO Auto-generated method stub
		DefaultLineMapper<PizzaTypes> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[] { "id","pizza_type_id", "name", "category", "ingredients"});
		lineTokenizer.setIncludedFields(new int[] {0,1,2,3,4});
		lineMapper.setLineTokenizer(lineTokenizer);
		BeanWrapperFieldSetMapper<PizzaTypes> fieldSetter = new BeanWrapperFieldSetMapper<>();
		fieldSetter.setTargetType(PizzaTypes.class);
		lineMapper.setFieldSetMapper(fieldSetter)		;
		return lineMapper;
	}
	
	
	
	@Bean
	public JdbcBatchItemWriter<PizzaTypes> writer(){
		JdbcBatchItemWriter<PizzaTypes> writer = new JdbcBatchItemWriter<>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<PizzaTypes>());
		writer.setSql("insert into pizza_types(pizza_type_id,name,category,ingredients)values(:pizzaTypeId,:name,:category,:ingredients)");
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
				.<PizzaTypes, PizzaTypes>chunk(5)
				.reader(reader())
				.writer(writer())
				.build();
}

}