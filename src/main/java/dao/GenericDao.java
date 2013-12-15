package dao;

import java.util.List;

import org.hibernate.Query;

import querycomposer.Root;


public class GenericDao {
	
	protected java.lang.Class<?> currentClass = null;
	
	// Fetch Functions
	
	protected List<?> getList(Root root) throws Exception { 
		List<?> resultList = null;
		try {
			
			Query query = root.prepareQuery();
			resultList = query.list();
			
		} catch(Exception e) {
			
			throw new Exception("Query Failed : " + e.getMessage());
			
		}
		return resultList;
	}
	

	protected Object getById(int id) throws Exception {
		Object result = null;
		try {
			
			result = HibernateUtil.getSession().get(currentClass, id);
			
		} catch(Exception e) {

			throw new Exception("Query Failed : " + e.getMessage());
			
		}
		return result;
	}
	
	// Create Functions
	
	protected void saveObject(Object object) throws Exception {
		try{
			
			HibernateUtil.startTransaction();
			HibernateUtil.getSession().save(currentClass.cast(object));

    	} catch(RuntimeException e) {

    		HibernateUtil.rollBackTransaction();
    		throw new Exception("Query Failed : " + e.getMessage());
			
    	} 
	}
	
	protected void saveObjectList(List<?> objectList) throws Exception {
		try{

			HibernateUtil.startTransaction();
			for(Object object : objectList)
				HibernateUtil.getSession().save(currentClass.cast(object));

    	} catch(RuntimeException e) {

    		HibernateUtil.rollBackTransaction();
    		throw new Exception("Query Failed : " + e.getMessage());
			
    	}
	}
	
	// Update Functions
	
	protected void updateObject(Object object) throws Exception {
		try{

			HibernateUtil.startTransaction();
			HibernateUtil.getSession().merge(currentClass.cast(object));
    		
    	} catch(RuntimeException e) {
			
    		HibernateUtil.rollBackTransaction();
    		throw new Exception("Query Failed : " + e.getMessage());
    		
    	} 
	}
	
	protected void updateObjectList(List<?> objectList) throws Exception {
		try{

			HibernateUtil.startTransaction();
			for(Object object : objectList)
				HibernateUtil.getSession().merge(currentClass.cast(object));

    	} catch(RuntimeException e) {

    		HibernateUtil.rollBackTransaction();
    		throw new Exception("Query Failed : " + e.getMessage());
			
    	}
	}
	
	// Delete Functions 
	
	protected void deleteObject(Object object) throws Exception {
		try{

			HibernateUtil.startTransaction();
			HibernateUtil.getSession().delete(currentClass.cast(object));
    		
    	} catch(RuntimeException e) {
			
    		HibernateUtil.rollBackTransaction();
    		throw new Exception("Query Failed : " + e.getMessage());
    		
    	} 
	}
	
	protected void deleteObjectList(List<?> objectList) throws Exception {
		try{

			HibernateUtil.startTransaction();
			for(Object object : objectList)
				HibernateUtil.getSession().delete(currentClass.cast(object));
    		
    	} catch(RuntimeException e) {
			
    		HibernateUtil.rollBackTransaction();
    		throw new Exception("Query Failed : " + e.getMessage());
    		
    	}
	}
	
}
