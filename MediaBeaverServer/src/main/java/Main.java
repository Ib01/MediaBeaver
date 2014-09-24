import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
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
	public static final String StartServer = "-start";
	public static final String StopServer = "-stop";
	private static Logger log = Logger.getLogger(Main.class.getName());
	private static Server jettyServer;
	
	private static class MonitorThread extends Thread 
	{
        private ServerSocket socket;
 
        public MonitorThread() 
        {
            setDaemon(true);
            setName("StopMonitor");
            try 
            {
                socket = new ServerSocket(8079, 1, InetAddress.getByName("127.0.0.1"));
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
 
        @Override
        public void run() 
        {
            System.out.println(">>> running jetty 'stop' thread");
            Socket accept;
            
            try 
            {
                accept = socket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                reader.readLine();
                
                System.out.println(">>> stopping jetty embedded server");
                
                jettyServer.stop();
                accept.close();
                socket.close();
            } catch(Exception e) 
            {
                throw new RuntimeException(e);
            }
        }
    }
	
	
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException 
	{	
		if(args[0].equalsIgnoreCase(StartServer))
		{
			startJetty();
			Thread monitor = new MonitorThread();
	        monitor.start();

		    log.debug("Joining Jetty Server");
		    jettyServer.join();
		    
		    return;
		}
		else if(args[0].equalsIgnoreCase(StopServer)){
			stopJetty();
			return;
		}
		
		
		showUsage();
	}

	
	public static void stopJetty() throws UnknownHostException, IOException
	{
		Socket s = new Socket(InetAddress.getByName("127.0.0.1"), 8079);
        OutputStream out = s.getOutputStream();
        
        log.debug(">>> Sending jetty stop request");
        
        out.write(("\r\n").getBytes());
        out.flush();
        s.close();
	}
	
	public static void startJetty()
	{
		try 
		{
			log.debug("Starting Jetty Server");
			jettyServer = new Server();

		    SelectChannelConnector connector = new SelectChannelConnector();
		    connector.setPort(8081);
		    jettyServer.addConnector(connector);

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
		    
		    jettyServer.setHandler(webapp);
		    jettyServer.start();
		    		
		} catch (Exception e) {
			log.error("En error occured while starting the jetty web server", e);
		}	
	}
	
	
	
	
	
	
	public static void showUsage()
	{
		System.out.println("");
		System.out.println("Usage: ");
		System.out.println("MediaBeaver "+StartServer);
		System.out.println("MediaBeaver "+StopServer);
		
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
