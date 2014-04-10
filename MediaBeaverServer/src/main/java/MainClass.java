

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.ProtectionDomain;
import java.util.List;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

import com.ibus.mediaBeaverServer.data.Repository;
import com.ibus.mediaBeaverServer.entity.MediaTransformConfig;


public class MainClass {

	public static void main(String[] args) 
	{
		try {
			
			System.out.println("Starting Jetty");
			
			startJetty();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	private static void startJetty() throws Exception 
	{
		Server server = new Server();

	    SelectChannelConnector connector = new SelectChannelConnector();
	    connector.setPort(8080);
	    server.addConnector(connector);

	    ProtectionDomain domain = MainClass.class.getProtectionDomain();
	    URL location = domain.getCodeSource().getLocation();
	    
	    WebAppContext webapp = new WebAppContext();
	    webapp.setContextPath("/");
	    webapp.setWar(location.toExternalForm());
	    
	    server.setHandler(webapp);
	    server.start();
	    server.join();
	
	}
	
	/*public static void WriteFile()
	{
		PrintWriter writer;
		try 
		{
			writer = new PrintWriter("C:\\DeleteMe.txt", "UTF-8");
			writer.println("The first line");
			writer.println("The second line");
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	

}
