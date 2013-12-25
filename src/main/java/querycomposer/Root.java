package querycomposer;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Query;

import helper.AbstractModelObject;
import dao.HibernateUtil;

public class Root extends AbstractModelObject {
	private String rootName;
	private java.lang.Class<?> rootClass;
	private Set<Attribute> attributeList = new HashSet<Attribute>();
	
	private String from = "";
	private String where = "";
	
	// Constructors
	
	public Root() {
		
	}
	
	public Root(String rootName) throws QueryComposerException {
		this.rootName = rootName;
		try {
			this.rootClass = Class.forName("dto." + rootName);
		} catch (ClassNotFoundException e) {
			throw new QueryComposerException("Root Class not found : " + rootName);
		}
	}
	
	public Root(java.lang.Class<?> rootClass) {
		this.rootClass = rootClass;
	}
	
	// Setters and Getters

	public String getrootName() {
		return rootName;
	}

	public void setrootName(String rootName) {
		String oldValue = this.rootName;
		this.rootName = rootName;
		firePropertyChange("name", oldValue, rootName);
	}

	public java.lang.Class<?> getRootClass() {
		return rootClass;
	}

	public void setRootClass(java.lang.Class<?> rootClass) {
		this.rootClass = rootClass;
	}

	public Set<Attribute> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(Set<Attribute> attributeList) {
		this.attributeList = attributeList;
	}
	
	// Adding Attributes
	
	public Root addAttribute(Attribute attribute) throws QueryComposerException {
		if(attribute!=null) {
			attribute.processAttribute(rootClass);
			this.attributeList.add(attribute);
		}
		return this;
	}

	public Root addAttributes(Attribute... attributes) throws QueryComposerException {
		for(Attribute attribute : attributes) {
			if(attribute!=null) {
				attribute.processAttribute(rootClass);
				this.attributeList.add(attribute);
			}
		}
		return this;
	}
	
	public void appendFrom(String from) {
		this.from += from + "\n";
	}
	
	public String getFromQuery() {
		return from;
	}

	public void appendWhere(String where) {
		if(this.where.equalsIgnoreCase("where "))
			this.where += where + "\n";
		else
			this.where += "and " + where + "\n";
	}
	
	public String getWhereQuery() {
		return where;
	}
	
	public String generateHql() throws QueryComposerException {
		
		from += "select " + rootName + " from " + rootName + " " + rootName + " \n";
		where += "where ";
		
		for(Attribute attribute : attributeList) {
			attribute.generateHql(rootName, this);
		}
		
		return getFromQuery() + getWhereQuery();
	}
	
	public Query prepareQuery() throws QueryComposerException {
		Query query = null;
		try {
			
			query = HibernateUtil.getSession().createQuery(generateHql());
			
			for(Attribute attribute : attributeList) {
				attribute.prepareQuery(query);
			}
			
		} catch (Exception e) {
			throw new QueryComposerException(e.getMessage());
		}
		
		return query;
		
	}
	
}
