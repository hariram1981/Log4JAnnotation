package com.hariram.annotation.log4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.xml.XMLLayout;

import com.hariram.annotation.AnnotationProcessor;

/**
 * @author hariram
 * date 25-Nov-2014
 */
public class Log4JAnnotationProcessor implements AnnotationProcessor {

	public enum APPENDER_TYPE {CONSOLE, FILE, ROLLINGFILE};
	
	public enum LOG_LEVEL {DEBUG, INFO, WARN, ERROR, FATAL};
	
	public enum LAYOUT {JSON, HTML, XML, PATTERN};

	public Object process(Object obj) {
		
		Class<? extends Object> objClass = obj.getClass();
		if(objClass.isAnnotationPresent(Log4J.class)){
			Log4J log4j = (Log4J) objClass.getAnnotation(Log4J.class);
			Logger classLogger = Logger.getLogger(objClass);

			if(classLogger.getAllAppenders().hasMoreElements()) {
				classLogger.removeAllAppenders();
			}
			classLogger.setLevel(getLevel(log4j.level()));
			classLogger.setAdditivity(false);
			
			getAppenders(log4j.appenders()).stream()
				.forEach(appender -> {
					Layout layout = getLayout(log4j.layout(), log4j.pattern());
					//If file or rolling then set file
					if(appender instanceof FileAppender) {
						FileAppender fileAppender = (FileAppender) appender;
						fileAppender.setFile(log4j.file());
						fileAppender.setLayout(layout);
						fileAppender.activateOptions();
						classLogger.addAppender(fileAppender);
					} else if(appender instanceof RollingFileAppender) {
						RollingFileAppender rollingFileAppender = (RollingFileAppender) appender;
						rollingFileAppender.setFile(log4j.file());
						rollingFileAppender.setMaximumFileSize(log4j.maxFiles());
						rollingFileAppender.setLayout(layout);
						rollingFileAppender.activateOptions();
						classLogger.addAppender(rollingFileAppender);
					} else {
						appender.setLayout(layout);
						appender.activateOptions();
						classLogger.addAppender(appender);
					}
			});

			try {
				Field field = objClass.getField("LOGGER");
				field.set(obj, classLogger);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}
		
/*		//Remove appenders from root logger so only logger of class logs
		if(Logger.getRootLogger().getAllAppenders().hasMoreElements()) {
			Logger.getRootLogger().removeAllAppenders();
		}
*/		
		return null;
	}
	
	/**
	 * 
	 * @param log4jlayout
	 * @return
	 */
	private Layout getLayout(LAYOUT log4jlayout, String pattern) {
		Layout layout;
		switch(log4jlayout) {
			case PATTERN:
				layout = new PatternLayout(pattern);
				break;
			case JSON:
				layout = new PatternLayout(pattern);
				break;
			case HTML:
				layout = new HTMLLayout();
				break;
			case XML:
				layout = new XMLLayout();
				break;
			default:
				layout = new PatternLayout(pattern);
				break;
		}
		return layout;
	}
	
	/**
	 * 
	 * @param appenderTypes
	 * @return
	 */
	private List<WriterAppender> getAppenders(APPENDER_TYPE[] appenderTypes) {
		List<WriterAppender> appenders = new ArrayList<WriterAppender>();
		Arrays.asList(appenderTypes).stream()
			.forEach(type -> {
				appenders.add(getAppender(type));
			});
		return appenders;
	}
	
	/**
	 * 
	 * @param appenderType
	 * @return
	 */
	private WriterAppender getAppender(APPENDER_TYPE appenderType) {
		WriterAppender appender;
		switch(appenderType) {
			case CONSOLE:
				appender = new ConsoleAppender();
				break;
			case FILE:
				appender = new FileAppender();
				break;
			case ROLLINGFILE:
				appender = new RollingFileAppender();
				break;
			default:
				appender = new ConsoleAppender();
				break;
		}
		return appender;
	}
	
	/**
	 * 
	 * @param logLevel
	 * @return
	 */
	private Level getLevel(LOG_LEVEL logLevel) {
		Level level;
		
		switch(logLevel) {
			case DEBUG:
				level = Level.DEBUG;
				break;
			case INFO:
				level = Level.INFO;
				break;
			case WARN:
				level = Level.WARN;
				break;
			case ERROR:
				level = Level.ERROR;
				break;
			case FATAL:
				level = Level.FATAL;
				break;
			default:
				level = Level.DEBUG;
				break;
		}
		
		return level;
	}

	public Object process(Object arg0, String arg1, Object[] arg2) {
		return null;
	}
}
