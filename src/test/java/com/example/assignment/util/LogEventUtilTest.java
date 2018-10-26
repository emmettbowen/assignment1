package com.example.assignment.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LogEventUtilTest {
	
	
	@Test
	public void testDurationFirstLogFirst () {
		
		assertEquals(4 , LogEventUtil.calculateDuration(1540460836703L, 1540460836707L) );
	}
	
	
	@Test
	public void testDurationFirstLogSecond () {
		
		assertEquals(4 , LogEventUtil.calculateDuration(1540460836707L, 1540460836703L) );
	}

}
