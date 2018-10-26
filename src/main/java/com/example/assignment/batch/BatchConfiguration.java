package com.example.assignment.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.example.assignment.domain.Event;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	
	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    
    private String fileName;

    private static final String OVERRIDDEN_BY_EXPRESSION = "tmp";
    
    
    @Bean 	
    @StepScope
    public FlatFileItemReader<String> reader(@Value("#{jobParameters[fileName]}") String fileName) {
    	
    	FlatFileItemReader<String> reader = new FlatFileItemReader<String>();
    	reader.setLineMapper(new PassThroughLineMapper());
    	reader.setStrict(false);
    	Resource resource = new FileSystemResource(fileName==null?"tmp":fileName);
    	reader.setResource(resource);
    	return reader;
    }
    
    
    @Bean
    public LogEventProcessor processor() {
        return new LogEventProcessor();
    }


    
    @Bean
    public JdbcBatchItemWriter<Event> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Event>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO EVENT (EVENT_ID, EVENT_DURATION,EVENT_TYPE,EVENT_HOST, EVENT_ALERT) VALUES (:id, :duration,:type,:host ,:alert )")
            .dataSource(dataSource)
            .build();
    }
    
    @Bean
    public Job importUserJob(Step step1) {
        return jobBuilderFactory.get("asignmentJob")
        		.incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Event> writer) {
        return stepBuilderFactory.get("step1")
            .<String, Event> chunk(10)
            .reader(reader(OVERRIDDEN_BY_EXPRESSION))
            .processor(processor())
            .writer(writer)
            .build();
    }

}
