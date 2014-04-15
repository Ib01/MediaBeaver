package com.ibus.mediabeaver.server.util;

public class Args 
{
	public static final String StartServer = "-server_start";
	public static final String ProcessMedia = "-process_media";
	
	public static String getAction(String[] args)
	{
		if(args.length == 0){
			System.out.println("This application could not be started: no arguments supplied");
			showUsage();
			return "";
		}
		
		String currentArg = "";
		for(String s : args)
		{
			if(s.equals(StartServer))
			{
				if(currentArg.length() > 0){
					System.out.println("This application could not be started: this application will only accept one argument");
					showUsage();
					return "";
				}
				currentArg = s;
			}
			else if(s.equals(ProcessMedia))
			{
				if(currentArg.length() > 0){
					System.out.println("This application could not be started: this application will only accept one argument");
					showUsage();
					return "";
				}
				currentArg = s;
			}
		}
		
		if(currentArg.length() == 0)
		{
			System.out.println("This application could not be started: no recognisable arguments found");
			showUsage();
			return "";
		}
		
		return currentArg;
	}
	
	public static void showUsage()
	{
		System.out.println("");
		System.out.println("Usage: ");
		System.out.println("MediaBeaver "+StartServer+ " [options]");
		System.out.println("MediaBeaver "+ProcessMedia+ " [options]");
		
	}
}
