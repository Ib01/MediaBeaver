package com.ibus.mediaBeaverBackend;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibus.mediaBeaverCore.data.HibernateUtil;
import com.ibus.mediaBeaverCore.util.MediaManager;



public class Main
{
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
