import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;

public class StopThread extends Thread
{
	public static final int StopThreadPort = 8079;
	public static final String StopThreadAddress = "127.0.0.1";
	private ServerSocket socket;
	private Logger log = Logger.getLogger(StopThread.class.getName());
	private Server jettyServer;
	
	public StopThread(Server jettyServer)
	{  
		this.jettyServer =jettyServer;
		setDaemon(true);
		setName("MediaBeaverStopMonitor");
		try
		{
			socket = new ServerSocket(StopThreadPort, 1,
					InetAddress.getByName(StopThreadAddress));
		} catch (Exception e)
		{
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
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					accept.getInputStream()));
			reader.readLine();

			log.debug("Stopping embeded jetty web server");
			jettyServer.stop();

			accept.close();
			socket.close();

			log.debug("The embeded jetty web server has stopped");
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}







