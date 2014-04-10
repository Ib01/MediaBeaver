

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

import com.ibus.mediaBeaverServer.data.DataInitialiser;
import com.ibus.mediaBeaverServer.data.Repository;
import com.ibus.mediaBeaverServer.entity.MediaTransformConfig;
import com.ibus.mediaBeaverServer.util.Args;
import com.ibus.mediaBeaverServer.util.WebServer;


public class MainClass 
{
	

	public static void main(String[] args) 
	{
		DataInitialiser.Initialise();
		String action = Args.getAction(args);
		
		if(action.equals(Args.StartServer))
		{
			WebServer.start();
		}
		else if(action.equals(Args.ProcessMedia)){
			processMedia();	
		} 
		
	}

	
	
	
	
	public static void processMedia()
	{
		
		System.out.println("processMedia");
		
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
