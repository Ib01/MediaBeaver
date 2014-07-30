import java.net.URL;
import java.security.ProtectionDomain;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main 
{
	static Logger log = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) 
	{	
		//DataInitialiser.Initialise();
		/*String action = Args.getAction(args);
		
		if(action.equals(Args.StartServer))
		{
			WebServer.start();
		}
		else if(action.equals(Args.ProcessMedia))
		{
			MediaManager m = new MediaManager();
			m.MoveMedia();
		} */
		
		startJetty();
		
	}

	public static void startJetty()
	{
		try 
		{
			log.debug("Starting Jetty Server");
			Server server = new Server();

		    SelectChannelConnector connector = new SelectChannelConnector();
		    connector.setPort(8081);
		    server.addConnector(connector);

		    ProtectionDomain domain = Main.class.getProtectionDomain();
		    URL location = domain.getCodeSource().getLocation();
		    
		    WebAppContext webapp = new WebAppContext();
		    webapp.setContextPath("/");
		    webapp.setWar(location.toExternalForm());

		    /*HandlerList handlers = new HandlerList();
		    handlers.setHandlers(new Handler[] {webapp, new ShutdownHandler(server, ShutdownPassword)});
		    server.setHandler(handlers);*/
		    
		    /*not sure if this session stuff works*/
		    SessionHandler sessionHandler = new SessionHandler(); 
	        SessionManager sessionManager = new HashSessionManager(); 
	        sessionHandler.setSessionManager(sessionManager); 
	        sessionManager.setMaxInactiveInterval(3600); 
		    
		    server.setHandler(webapp);
		    server.start();
			
		    log.debug("Joinging Jetty Server");
		    server.join();
		    		
		} catch (Exception e) {
			log.error("En error occured while starting the jetty web server", e);
		}	
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
