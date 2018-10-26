package com.example.assignment.batch;

import java.util.HashMap;

import org.springframework.batch.item.ItemProcessor;

import com.example.assignment.domain.Event;
import com.example.assignment.domain.Log;
import com.example.assignment.util.LogEventUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
@Slf4j

public class LogEventProcessor implements ItemProcessor<String, Event> {
	
	
	private HashMap<String , Log> logMap;

	public LogEventProcessor () {	
		logMap = new HashMap<>();
	}
	@Override
	public Event process(String item) throws Exception {
		
		log.info("Processing log record "+ item);
		
		ObjectMapper objectMapper = new ObjectMapper();
		Log currentLog = objectMapper.readValue(item, Log.class);  
		Log correspondingLog = null ;
		if (!logMap.containsKey(currentLog.getId())) {
			logMap.put(currentLog.getId(), currentLog);
		}else {
			correspondingLog = logMap.get(currentLog.getId());
			long duration = LogEventUtil.calculateDuration(correspondingLog.getTimestamp(), currentLog.getTimestamp());
			logMap.remove(currentLog.getId());
			Event logEvent = Event.builder().id(currentLog.getId()).type(currentLog.getType()).host(currentLog.getHost()).duration(duration).build();
			
			return logEvent;
		}
		
		return null;
		
	}

	
	
}
