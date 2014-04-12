import java.net.URL;
import java.security.ProtectionDomain;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import com.ibus.mediaBeaverCore.util.MediaManager;

public class MainClass 
{
	//static Logger log;

	public static void main(String[] args) 
	{
		MediaManager m;
		
		/*MediaManager h = new MediaManager();
		h.MoveMedia();*/

		
		/*com.ibus.mediaBeaverCore.util
		
		MediaManager*/
		
		/*LogManager.resetConfiguration();
		//BasicConfigurator.configure();
		PropertyConfigurator.configure("log4j.properties");
		
		log = Logger.getLogger(MainClass.class.getName());
		
		log.info("******************** Testing *************************");*/
	
		
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
			System.out.println("Starting Jetty Server");
			System.out.println("***************************************");
			
			Server server = new Server();

		    SelectChannelConnector connector = new SelectChannelConnector();
		    connector.setPort(8080);
		    server.addConnector(connector);

		    ProtectionDomain domain = MainClass.class.getProtectionDomain();
		    URL location = domain.getCodeSource().getLocation();
		    
		    WebAppContext webapp = new WebAppContext();
		    webapp.setContextPath("/");
		    webapp.setWar(location.toExternalForm());

		    /*HandlerList handlers = new HandlerList();
		    handlers.setHandlers(new Handler[] {webapp, new ShutdownHandler(server, ShutdownPassword)});
		    server.setHandler(handlers);*/
		    
		    server.setHandler(webapp);
		    server.start();
		    server.join();
		    System.out.println("***************************************");
		    System.out.println("Jetty Server Started Successfully");	

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
