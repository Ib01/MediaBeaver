package com.ibus.opensubtitles.client.utilities;

public class OpenSubtitlesHashData
{
	private String hashData;
	private String totalBytes;
	public String getHashData()
	{
		return hashData;
	}
	public void setHashData(String hashData)
	{
		this.hashData = hashData;
	}
	public String getTotalBytes()
	{
		return totalBytes;
	}
	public void setTotalBytes(String totalBytes)
	{
		this.totalBytes = totalBytes;
	}
	
	public boolean isValid()
	{
		return (hashData != null && hashData.length() > 0 && totalBytes != null && totalBytes.length() > 0);
	}
}
