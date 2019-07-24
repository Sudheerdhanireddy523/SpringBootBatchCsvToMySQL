package com.app.config;


import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.app.model.Product;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	//job
	@Autowired
	private JobBuilderFactory jf;
	
	@Bean
	public Job j1() {
		return jf.get("j1").incrementer(new RunIdIncrementer()).start(s1()).build();
	}
	
	//step
	@Autowired
	
	private StepBuilderFactory sf;
	
	@Bean
	public Step s1() {
		return sf.get("s1").<Product,Product>chunk(3).reader(reader()).processor(processor()).writer(writer()).build();
	}
	
@Bean
public ItemReader<Product> reader(){
	FlatFileItemReader<Product> reader=new FlatFileItemReader<>();
	//loading file resource
	//reader.setResource(new FileSystemResource("D:/ab/products.csv"));
	reader.setResource(new ClassPathResource("products.csv"));
	
	// -- read data line by line
			reader.setLineMapper(new DefaultLineMapper<Product>() {{
				//-- make one into multiple parts
				setLineTokenizer(new DelimitedLineTokenizer() {{
					 //stores as variables with names
					setNames("id","code","cost");
				}});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {{
					//convert to model class object
					setTargetType(Product.class);
				}});
			}});        
			
			return reader;
		}
		
		//processor bean
		@Bean
		public ItemProcessor<Product, Product> processor(){
			//return new MyProcessor();
			return (p)->{
				p.setDisc(p.getCost()*3/100.0);
				p.setGst(p.getCost()*12/100.0);
				return p;
			};
		}
		
		//writer
		@Bean
		public ItemWriter<Product> writer(){
			JdbcBatchItemWriter<Product> writer=new JdbcBatchItemWriter<>();
			writer.setDataSource(dataSource());
			writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Product>());
			writer.setSql("INSERT INTO PRODSTAB (ID,CODE,COST,DISC,GST) VALUES (:id,:code,:cost,:disc,:gst)");
			return writer;
		}

		@Bean
		public DataSource dataSource() {
			DriverManagerDataSource ds=new DriverManagerDataSource();
			ds.setDriverClassName("com.mysql.jdbc.Driver");
			ds.setUrl("jdbc:mysql://localhost:3306/batch");
			ds.setUsername("root");
			ds.setPassword("Sudha@123");
return ds;
		}
		

}
