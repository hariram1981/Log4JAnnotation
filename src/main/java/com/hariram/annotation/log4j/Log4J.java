package com.hariram.annotation.log4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hariram.annotation.log4j.Log4JAnnotationProcessor.APPENDER_TYPE;
import com.hariram.annotation.log4j.Log4JAnnotationProcessor.LAYOUT;
import com.hariram.annotation.log4j.Log4JAnnotationProcessor.LOG_LEVEL;

@Retention(RetentionPolicy.RUNTIME)

@Target(ElementType.TYPE)
public @interface Log4J {
	APPENDER_TYPE[] appenders() default {APPENDER_TYPE.CONSOLE};
	
	LAYOUT layout() default LAYOUT.PATTERN;
	
	String pattern() default "%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n";
	
	LOG_LEVEL level() default LOG_LEVEL.DEBUG;
	
	String file() default "";
	
	long maxFiles() default 5;
}

