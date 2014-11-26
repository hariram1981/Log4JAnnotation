package com.hariram.annotation.log4j;

import org.junit.Test;

import com.hariram.annotation.log4j.sample.SampleConsoleLog;
import com.hariram.annotation.log4j.sample.SampleFileLog;

/**
 * @author hariram
 * date 25-Nov-2014
 */
public class Log4JAnnotationTest {

	//@Test
	public void testConsoleLog() {
		SampleConsoleLog.logDetails();
	}

	@Test
	public void testFileLog() {
		SampleFileLog.logDetails();
	}
}
