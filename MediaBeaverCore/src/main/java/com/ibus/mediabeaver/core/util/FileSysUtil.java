package com.ibus.mediabeaver.core.util;

import org.apache.commons.io.FilenameUtils;

public class FileSysUtil 
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
	
	public static String getExtension(String path)
	{
		return FilenameUtils.getExtension(path);
	}
}
