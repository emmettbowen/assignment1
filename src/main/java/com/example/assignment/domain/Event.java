package com.example.assignment.domain;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class Event {
	String id;
	String type;
	String host;
	Long duration;
	
	public boolean isAlert () {
		return duration > 4;
	}
	

}
