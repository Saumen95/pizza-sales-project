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

import com.elrich.pizzasalesproject.models.OrderDetails;

public class BatchOrderDetailsConfig {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JobBuilder jobBuilder;
	
	@Bean
	public FlatFileItemReader<OrderDetails> reader(){
		FlatFileItemReader<OrderDetails> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("order_details.csv"));
		reader.setLineMapper(getLinemapper());
		reader.setLinesToSkip(1);
		return reader;		
		
	}

	private LineMapper<OrderDetails> getLinemapper() {
		// TODO Auto-generated method stub
		DefaultLineMapper<OrderDetails> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[] { "id","order_id", "pizza_id", "quantity"});
		lineTokenizer.setIncludedFields(new int[] {0,1,2,3});
		lineMapper.setLineTokenizer(lineTokenizer);
		BeanWrapperFieldSetMapper<OrderDetails> fieldSetter = new BeanWrapperFieldSetMapper<>();
		fieldSetter.setTargetType(OrderDetails.class);
		lineMapper.setFieldSetMapper(fieldSetter)		;
		return lineMapper;
	}
	
	@Bean
	public OrderDetailsItemProcessor processor() {
		return new OrderDetailsItemProcessor();
		
	}
	
	@Bean
	public JdbcBatchItemWriter<OrderDetails> writer(){
		JdbcBatchItemWriter<OrderDetails> writer = new JdbcBatchItemWriter<>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<OrderDetails>());
		writer.setSql("insert into order_details(order_id,pizza_id,quantity)values(:orderId,:pizzaId,:quantity)");
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
				.<OrderDetails, OrderDetails>chunk(5)
				.reader(reader())
				.writer(writer())
				.build();
}

}
