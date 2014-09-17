package com.ibus.mediabeaver.server.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SessionExpiredInterceptor implements HandlerInterceptor
{

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		/*MediaConfigViewModel config = (MediaConfigViewModel)request.getSession().getAttribute("config");
		
		if(config == null)
		{
			String h = request.getRequestURI();
			
			if(request.getRequestURI().contains("configWizard"))
			{
				response.sendRedirect("/configList"); //TODO: redirect to a dedicated session expired page.
				return false;
			}
		}
		*/
		return true;
	}

	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
	{
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
	{
	}

}
