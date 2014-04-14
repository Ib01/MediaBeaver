package com.ibus.mediaBeaverServer.util;

import java.net.URL;
import java.security.ProtectionDomain;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ShutdownHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebServer 
{
	private static final String ShutdownPassword ="doit"; 
	
	public static void start()
	{
		try 
		{
			System.out.println("Starting Jetty Server");
			System.out.println("***************************************");
			
			Server server = new Server();

		    SelectChannelConnector connector = new SelectChannelConnector();
		    connector.setPort(8080);
		    server.addConnector(connector);

		    ProtectionDomain domain = WebServer.class.getProtectionDomain();
		    URL location = domain.getCodeSource().getLocation();
		    
		    WebAppContext webapp = new WebAppContext();
		    webapp.setContextPath("/");
		    webapp.setWar(location.toExternalForm());

		    HandlerList handlers = new HandlerList();
		    handlers.setHandlers(new Handler[] {webapp, new ShutdownHandler(server, ShutdownPassword)});
		    server.setHandler(handlers);
		    
		    //server.setHandler(webapp);
		    server.start();
		    server.join();
		    System.out.println("***************************************");
		    System.out.println("Jetty Server Started Successfully");	
		    
		    
		    
		    
		     

		    

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
