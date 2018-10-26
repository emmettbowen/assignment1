package com.example.assignment.batch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.example.assignment.domain.Event;

public class LogEventProcessorTest {
	
	@Test
	public void testLogIsAlert () throws Exception {
		LogEventProcessor logEventProcessor = new LogEventProcessor();
		String line1 = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\",\"host\":\"12345\", \"timestamp\":1491377495212}";
		String line2 = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\",\"host\":\"12345\", \"timestamp\":1491377495217}";
		
		logEventProcessor.process(line1); 
		Event event = logEventProcessor.process(line2);
		
		assertTrue (event.isAlert());
		
	}
	
	
	@Test
	public void testLogIsNotAlert () throws Exception {
		LogEventProcessor logEventProcessor = new LogEventProcessor();
		String line1 = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\",\"host\":\"12345\", \"timestamp\":1491377495212}";
		String line2 = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\",\"host\":\"12345\", \"timestamp\":1491377495214}";
		
		logEventProcessor.process(line1); 
		Event event = logEventProcessor.process(line2);
		
		assertFalse (event.isAlert());
		
	}

}
