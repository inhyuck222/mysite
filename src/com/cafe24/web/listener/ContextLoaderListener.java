package com.cafe24.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//@WebListener
public class ContextLoaderListener implements ServletContextListener {

	// Application 실행시에 실행
    public void contextInitialized(ServletContextEvent servletContextEvent)  {
    	String contextConfigLocation = servletContextEvent.getServletContext().getInitParameter("contextConfigLocation");
    	System.out.println("Container Starts.." + contextConfigLocation);
    }
    
    public void contextDestroyed(ServletContextEvent servletContextEvent)  { 
    	
    }
	
}
