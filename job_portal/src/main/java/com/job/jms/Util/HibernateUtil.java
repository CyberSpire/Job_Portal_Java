package com.job.jms.Util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.job.jms.Entity.*;

//Configuration is a class activates the Hibernate framework and reads both mappings and configuration files.
public class HibernateUtil 
{
	//The SessionFactory is a factory of session and client of ConnectionProvider. It holds a second level cache (optional) of data.
	private static final SessionFactory sessionFactory = buildSessionFactory();
	//buildSessionFactory() method gathers the meta-data which is in the cfg Object.
	private static SessionFactory buildSessionFactory() 
	{
		try 
		{
			return new
			//It checks whether the config file is syntactically correct or not,if not valid it will throw new exception
			Configuration().configure("hibernate.cfg.xml")
			.addAnnotatedClass(Candidate.class)
			.addAnnotatedClass(Recruiter.class)
			.addAnnotatedClass(Job.class)
			.addAnnotatedClass(Application.class)
			.buildSessionFactory();
		} 
		catch (Throwable ex) 
		{
			throw new ExceptionInInitializerError(ex);
		}
	}
	//If it is valid then it creates a meta-data in memory and returns meta-data to object to represent config file.
	public static SessionFactory getSessionFactory() 
	{
		return sessionFactory;
	}
}