package com.ibus.mediaBeaverServer.data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class HibernateRequestInterceptor implements HandlerInterceptor  
{
	
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler) throws Exception 
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		
		if(!s.getTransaction().isActive())
			s.beginTransaction();
		
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView) throws Exception 
	{
		   Transaction tx = null;
		   try
		   {
			   //note: current session should be closed automatically when transaction is committed or rolled back
			   Session s = HibernateUtil.getSessionFactory().getCurrentSession();
			   tx = s.getTransaction();
			   tx.commit();
			   
		   }
		   catch(RuntimeException e)
		   {
			   tx.rollback();
			   throw e;
			   
			   //TODO: implement logging
			   	/*try
			   	{
			   		tx.rollback();
			   	}catch(RuntimeException rbe)
			   	{
			   		//log.error("Couldn’t roll back transaction", rbe);
			   	}
			   	throw e;*/
		   }
		
		
		
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception 
	{
		
		int i = 0;
		int p = i;
	}

	
}
