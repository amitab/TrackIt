package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
	private static SessionFactory sessionFactory = null;
	private static Session session = null;
	protected static Transaction transaction = null;
	
	public static void openSession() throws HibernateException {
		session = getSessionFactory().openSession();
	}
	
	public static Session getSession() throws HibernateException {
		if(session == null) {
			openSession();
		}
		return session;
	}
	
	protected static void startTransaction() throws HibernateException {
		transaction = session.beginTransaction();
		transaction.setTimeout(5);
	}
	
	protected static void commitTransaction() throws HibernateException {
		if(transaction != null) {
			transaction.commit();
		}
	}
	
	protected static void rollBackTransaction() throws HibernateException {
		if(transaction != null) {
			transaction.rollback();
		}
	}
	
	public static void closeSession() {
		session.close();
		session = null;
	}
	
	private static void buildFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		sessionFactory = new Configuration().configure().buildSessionFactory(serviceRegistry);
	}
	
	public static SessionFactory getSessionFactory() {
		if(sessionFactory == null) buildFactory();
        return sessionFactory;
    }
 
    public static void shutdown() {
    	// Close caches and connection pools
    	getSessionFactory().close();
    	sessionFactory = null;
    }
	
}
