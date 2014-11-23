package com.ibus.mediabeaver.server.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class HotlinkedImage extends HttpServlet 
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		String imgUri = req.getParameter("imgUri");
		URL url = new URL(String.format(imgUri));
		
		InputStream in = new BufferedInputStream(url.openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1!=(n=in.read(buf)))
		{
		   out.write(buf, 0, n);
		}
		out.close();
		in.close();
		byte[] response = out.toByteArray();
		
		resp.setContentType("image/jpeg");
		resp.getOutputStream().write(response);
	}
}
