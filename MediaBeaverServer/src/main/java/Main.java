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


public class Main 
{
	public static final int StopThreadPort = 8079;
	public static final String StopThreadAddress = "127.0.0.1";
	public static final String StartServer = "-start";
	public static final String StopServer = "-stop";
	private static Logger log = Logger.getLogger(Main.class.getName());
	private static Server jettyServer;
	
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException 
	{	
		if(args.length > 0)
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
			else if(args[0].equalsIgnoreCase(StopServer))
			{
				stopJetty();
				return;
			}
		}
		
		showUsage();
	}

	public static void showUsage()
	{
		System.out.println("");
		System.out.println("Usage: ");
		System.out.println("MediaBeaver "+StartServer);
		System.out.println("MediaBeaver "+StopServer);
		
	}
	
	
	public static void stopJetty() throws UnknownHostException, IOException
	{
		Socket socket = new Socket(InetAddress.getByName(StopThreadAddress), StopThreadPort);
        OutputStream outStream = socket.getOutputStream();
        
        log.debug("Sending jetty stop request");
        
        outStream.write(("\r\n").getBytes());
        outStream.flush();
        socket.close();
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
	
	public static class MonitorThread extends Thread 
	{
        private ServerSocket socket;
 
        public MonitorThread() 
        {
            setDaemon(true);
            setName("MediaBeaverStopMonitor");
            try 
            {
                socket = new ServerSocket(StopThreadPort, 1, InetAddress.getByName(StopThreadAddress));
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
 
        @Override
        public void run() 
        {
        	log.debug("Running jetty stop thread");
            Socket accept;
            
            try 
            {
                accept = socket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                reader.readLine();
                
                log.debug("Stopping embeded jetty web server");
                jettyServer.stop();
                
                accept.close();
                socket.close();
                
                log.debug("The embeded jetty web server has stopped");
            } catch(Exception e) 
            {
                throw new RuntimeException(e);
            }
        }
    }
	
	

}
