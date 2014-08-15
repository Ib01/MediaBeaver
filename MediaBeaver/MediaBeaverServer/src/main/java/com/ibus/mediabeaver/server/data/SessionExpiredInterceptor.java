package com.ibus.mediabeaver.server.data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;

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
