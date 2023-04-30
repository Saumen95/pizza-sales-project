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

import com.elrich.pizzasalesproject.models.Orders;


@Configuration
@EnableBatchProcessing
public class BatchOrderConfig {
	
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JobBuilder jobBuilder;
	
	@Bean
	public FlatFileItemReader<Orders> reader(){
		FlatFileItemReader<Orders> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("orders.csv"));
		reader.setLineMapper(getLinemapper());
		reader.setLinesToSkip(1);
		return reader;		
		
	}

	private LineMapper<Orders> getLinemapper() {
		// TODO Auto-generated method stub
		DefaultLineMapper<Orders> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[] { "id","order_id", "order_date", "time"});
		lineTokenizer.setIncludedFields(new int[] {0,1,2});
		lineMapper.setLineTokenizer(lineTokenizer);
		BeanWrapperFieldSetMapper<Orders> fieldSetter = new BeanWrapperFieldSetMapper<>();
		fieldSetter.setTargetType(Orders.class);
		lineMapper.setFieldSetMapper(fieldSetter)		;
		return lineMapper;
	}
	
	@Bean
	public OrderItemProcessor processor() {
		return new OrderItemProcessor();
		
	}
	
	@Bean
	public JdbcBatchItemWriter<Orders> writer(){
		JdbcBatchItemWriter<Orders> writer = new JdbcBatchItemWriter<>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Orders>());
		writer.setSql("insert into orders(order_id, order_date,order_time)values(:orderId,:orderDate,:time)");
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
				.<Orders, Orders>chunk(5)
				.reader(reader())
				.writer(writer())
				.build();
}
}
