

import com.ibus.mediaBeaverServer.data.DataInitialiser;
import com.ibus.mediaBeaverServer.util.Args;
import com.ibus.mediaBeaverServer.util.MediaManager;
import com.ibus.mediaBeaverServer.util.WebServer;


public class MainClass 
{
	//static Logger log;

	public static void main(String[] args) 
	{
		/*LogManager.resetConfiguration();
		//BasicConfigurator.configure();
		PropertyConfigurator.configure("log4j.properties");
		
		log = Logger.getLogger(MainClass.class.getName());
		
		log.info("******************** Testing *************************");*/
	
		
		DataInitialiser.Initialise();
		String action = Args.getAction(args);
		
		if(action.equals(Args.StartServer))
		{
			WebServer.start();
		}
		else if(action.equals(Args.ProcessMedia))
		{
			MediaManager m = new MediaManager();
			m.MoveMedia();
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
