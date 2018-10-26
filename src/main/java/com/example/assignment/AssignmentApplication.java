package com.example.assignment;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootApplication
public class AssignmentApplication implements CommandLineRunner{
	
	 @Autowired
	 JobLauncher jobLauncher;
	     
	 @Autowired
	  Job job;
	 

	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
	}
	
	

	    @Override
	    public void run(String... args) throws Exception
	    {
	        JobParameters params = new JobParametersBuilder()
	                    .addString("fileName", args[0])
	                    .toJobParameters();
	        try {
	        jobLauncher.run(job, params);
	        }catch (JobInstanceAlreadyCompleteException e){
	        	log.error("File already processed "+ args[0]);
	        }
	    }
}
