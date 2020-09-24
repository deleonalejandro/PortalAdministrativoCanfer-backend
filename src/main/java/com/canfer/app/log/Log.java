package com.canfer.app.log;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

 

public class Log {

    
    public static void activity(String message, String empresa) {
        
        final Level activity = Level.forName("ACTIVIDAD", 150);
        ThreadContext.put("empresa", empresa);
        
        final Logger logger = LogManager.getLogger();
        
        logger.log(activity, message);  
         
        //Clear the map
        ThreadContext.clearMap();
        
    }
    
    public static void falla(String message) {
        
        final Level activity = Level.forName("FALLA", 140);
        ThreadContext.put("empresa", "NA");
        
        final Logger logger = LogManager.getLogger();
        
        logger.log(activity, message);  	
         
        //Clear the map
        ThreadContext.clearMap();
        
    }

	public static void warn(String message) {
		
		 final Logger logger = LogManager.getLogger();

	        logger.warn(message);  
		
	}
	
	public static void error(String message) {
		
		 final Logger logger = LogManager.getLogger();

	        logger.error(message);  
		
	}
 }