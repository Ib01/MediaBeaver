import java.net.URL;
import java.security.ProtectionDomain;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;


/*TODO
 * 
 * run jetty in a thread allow starting and stopping 
 * */
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
		    
		    /*not sure if this session stuff works*/
		    /*SessionHandler sh = webapp.getSessionHandler();
		    SessionManager sm = sh.getSessionManager();
            sm.setMaxInactiveInterval(60*60);*/
            
		    /*HandlerList handlers = new HandlerList();
		    handlers.setHandlers(new Handler[] {webapp, new ShutdownHandler(server, ShutdownPassword)});
		    server.setHandler(handlers);*/
		    
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
