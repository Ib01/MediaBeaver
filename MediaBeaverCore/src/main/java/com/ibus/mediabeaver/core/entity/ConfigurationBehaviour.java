package com.ibus.mediabeaver.core.entity;

public class ConfigurationBehaviour 
{
	
	public static boolean isVideoExtension(String extension, String videoExtensionFilter)
	{
		String[] extensions = videoExtensionFilter.split("\\s*,\\s*");
		
		for(String allowedExtension : extensions)
		{
			extension = extension.trim().replace(".", "");
			allowedExtension = allowedExtension.trim().replace(".", "");
			
			if(extension.equals(allowedExtension))
				return true;
		}
		
		return false;
	}
}
