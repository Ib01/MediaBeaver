package com.ibus.mediabeaver.server.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Configuration;

public class RequestInterceptor implements HandlerInterceptor
{
	private static boolean appInitialised = false;
	

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		//we do not want to start a transaction when resources are called (scripts, css etc ... )
		if(request.toString().contains("resources/"))
			return true;	
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		if(!appInitialised)
		{
			appInitialised = true;
			
			Configuration config = Repository.getFirstEntity(Configuration.class);
			if(config == null)
			{
				transaction.commit();
				response.sendRedirect("/configuration/welcome");
				return false;
			}
		}
		
		return true;
	}

	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
	{
		if(request.toString().contains("resources/"))
			return;
		
		Transaction tx = null;
		try
		{
			// note: current session should be closed automatically when
			// transaction is committed or rolled back
			Session s = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = s.getTransaction();
			
			if(tx != null)
				tx.commit();

		} catch (RuntimeException e)
		{
			tx.rollback();
			throw e;

			// TODO: implement logging
			 //try { tx.rollback(); }catch(RuntimeException rbe) {
			 //log.error("Couldn�t roll back transaction", rbe); } throw e;
			 
		}

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
	{
	}

}
