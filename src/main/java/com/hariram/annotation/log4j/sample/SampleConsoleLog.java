package com.hariram.annotation.log4j.sample;

import org.apache.log4j.Logger;

import com.hariram.annotation.log4j.Log4J;
import com.hariram.annotation.log4j.Log4JAnnotationProcessor;
import com.hariram.annotation.log4j.Log4JAnnotationProcessor.APPENDER_TYPE;
import com.hariram.annotation.log4j.Log4JAnnotationProcessor.LAYOUT;
import com.hariram.annotation.log4j.Log4JAnnotationProcessor.LOG_LEVEL;
import com.hariram.annotation.util.AnnotationUtil;

/**
 * @author hariram
 * date 25-Nov-2014
 */
@Log4J(
		appenders={APPENDER_TYPE.CONSOLE},
		pattern="%-5p %c{1}:%L - %m%n",
		layout=LAYOUT.PATTERN,
		level=LOG_LEVEL.INFO
		)
public class SampleConsoleLog {
	public static Logger LOGGER = null;
	static {
		/*AnnotationProcessor processor = new Log4JAnnotationProcessor();
		processor.process(new SampleClass());*/
		AnnotationUtil.processAnnotation(new Log4JAnnotationProcessor(), new SampleConsoleLog());
	}
	
	public static void logDetails() {
		LOGGER.debug("Logging debug");
		LOGGER.info("Logging info");
	}
}
