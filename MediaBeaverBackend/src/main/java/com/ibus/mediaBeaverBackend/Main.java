package com.ibus.mediaBeaverBackend;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibus.mediaBeaverBackend.utility.MediaManager;
import com.ibus.mediaBeaverCore.data.HibernateUtil;

public class Main
{
	static Logger log = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] args)
	{
		MediaManager h = new MediaManager();
		h.MoveMedia();

		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = s.beginTransaction();


		tx.commit();

		
		System.out.println("succeess");
	}

}
