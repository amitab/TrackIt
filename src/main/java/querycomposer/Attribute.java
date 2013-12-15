package querycomposer;

import helper.ConversionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;

public class Attribute {
	public static final int OP_EQUAL = 0;
	public static final int OP_NOT_EQUAL = 1;
	public static final int OP_LESS_THAN = 2;
	public static final int OP_GREATER_THAN = 3;
	public static final int OP_LESS_OR_EQUAL = 4;
	public static final int OP_GREATER_OR_EQUAL = 5;
	public static final int OP_LIKE = 6;
	public static final int OP_ILIKE = 7;
	public static final int OP_IN = 8;
	public static final int OP_NOT_IN = 9;
	public static final int OP_NULL = 10;
	public static final int OP_NOT_NULL = 11;
	public static final int OP_EMPTY = 12;
	public static final int OP_NOT_EMPTY = 13;
	public static final int OP_BETWEEN = 14;
	
	
	private String attributeName = null;
	private java.lang.Class<?> attributeClass = null;
	private java.lang.reflect.Field attributeField = null;
	private Object value = null;
	private String query = null;
	private int operation;

	private boolean foreignKey;
	private Set<Attribute> attributeList = new HashSet<Attribute>();
	
	private String where = "";
	private String from = "";
	
	// Date specific
	private String dateFormat = null;
	
	// Constructors
	
	public Attribute() {
		
	}
	
	public Attribute(String attributeName) {
		this.attributeName = attributeName;
		this.foreignKey = true;
	}
	
	public Attribute(String attributeName, String query) {
		this.attributeName = attributeName;
		this.query = query;
		this.foreignKey = false;
		this.operation = OP_EQUAL;
	}

	public Attribute(String attributeName, String query, int operation) {
		this.attributeName = attributeName;
		this.query = query;
		this.foreignKey = false;
		this.operation = operation;
	}

	public Attribute(String attributeName, int operation) {
		this.attributeName = attributeName;
		this.query = null;
		this.foreignKey = false;
		this.operation = operation;
	}
	
	// Static Instantiators
	
	public static Attribute newInstance(String attributeName) {
		return new Attribute(attributeName);
	}
	
	public static Attribute newInstance(String attributeName, String query) {
		return new Attribute(attributeName, query);
	}

	public static Attribute newInstance(String attributeName, String query, int operation) {
		return new Attribute(attributeName, query, operation);
	}

	public static Attribute newInstance(String attributeName, int operation) {
		return new Attribute(attributeName, operation);
	}
	
	// Getters and Setters

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public java.lang.Class<?> getAttributeClass() {
		return attributeClass;
	}

	public void setAttributeClass(java.lang.Class<?> attributeClass) {
		this.attributeClass = attributeClass;
	}

	public java.lang.reflect.Field getAttributeField() {
		return attributeField;
	}

	public void setAttributeField(java.lang.reflect.Field attributeField) {
		this.attributeField = attributeField;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public boolean isForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(boolean foreignKey) {
		this.foreignKey = foreignKey;
	}

	public Set<Attribute> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(Set<Attribute> attributeList) throws QueryComposerException {
		if(!this.foreignKey) {
			throw new QueryComposerException("The attribute '" + attributeName + "' cannot have attributes joined to it.");
		}
		this.attributeList = attributeList;
	}
	
	public List<?> getValuesAsList() {
		if (value == null) {
			return null;
		} else if (value instanceof List<?>) {
			return (List<?>) value;
		} else if (value instanceof Collection<?>) {
			return new ArrayList<Object>((Collection<?>) value);
		} else if (value.getClass().isArray()) {
			ArrayList<Object> list = new ArrayList<Object>(Array.getLength(value));
			for (int i = 0; i < Array.getLength(value); i++) {
				list.add(Array.get(value, i));
			}
			return list;
		} else {
			return Collections.singletonList(value);
		}
	}
	
	// Add Attributes
	
	public Attribute addAttribute(Attribute attribute) throws QueryComposerException {
		if(!this.foreignKey) {
			throw new QueryComposerException("The attribute '" + attributeName + "' cannot have attributes joined to it.");
		}
		this.attributeList.add(attribute);
		return this;
	}
	
	public Attribute addAttributes(Attribute... attributes) throws QueryComposerException {
		if(!this.foreignKey) {
			throw new QueryComposerException("The attribute '" + attributeName + "' cannot have attributes joined to it.");
		}
		for(Attribute attribute : attributes) {
			this.attributeList.add(attribute);
		}
		return this;
	}
	
	// process the attribute
	
	public void processAttribute(java.lang.Class<?> rootClass) throws QueryComposerException {
		try {
			if(this.attributeName != null)
				this.attributeField = rootClass.getDeclaredField(this.attributeName);
		} catch (Exception e) {
			throw new QueryComposerException("Didn't find attribute : " + this.attributeName + " for root Class " + rootClass.getSimpleName());
		} 
		
		if(this.foreignKey) {
			Annotation []annotations = this.attributeField.getDeclaredAnnotations();
			for(Annotation annotation : annotations){
				
				// Find the association class
				
				if(annotation instanceof annotation.AssociationType) {
					annotation.AssociationType associationType = (annotation.AssociationType) annotation;
					try {
						this.attributeClass = Class.forName("dto." + associationType.type());
					} catch (ClassNotFoundException e) {
						throw new QueryComposerException("Could not determine class of '" + attributeName + "'. Check if you set the annotation for it.");
					}
				}
			}
			
			for(Attribute attribute : attributeList) {
				attribute.processAttribute(attributeClass);
			}
			
		} else {

			this.attributeClass = this.attributeField.getType();
			processQuery();
			
		}
	}
	
	private void processQuery() throws QueryComposerException {
		try {
			
			if(operation == OP_NOT_NULL || operation == OP_NULL || operation == OP_EMPTY || operation == OP_NOT_EMPTY) {
				if(query != null) {
					throw new QueryComposerException("Operation " + operation + " can't have arguments.");
				} else return;
			}
			
			List<Object> dataObjects = new ArrayList<Object>();
			String[] splitString = (query.split(","));
			
			if(splitString.length == 1) {

				if(operation == OP_BETWEEN ) {
					throw new QueryComposerException("Operation " + operation + " must have 2 arguments.");
				}
				
				value = ConversionUtil.convertIfNeeded(splitString[0], attributeClass);
				
			} else {
				
				if(operation != OP_IN || operation != OP_NOT_IN || operation != OP_BETWEEN ) {
					throw new QueryComposerException("Operation " + operation + " must have only 1 argument.");
				}
				
				if(operation == OP_BETWEEN ) {
					throw new QueryComposerException("Operation " + operation + " must have 2 arguments.");
				}
				
				for(String string : splitString) {
					Object data = string.trim();
					data = ConversionUtil.convertIfNeeded(data, attributeClass);
					dataObjects.add(data);
				}
				
				value = dataObjects;
			}
			
			// If there was a date conversion, get the format
			if(attributeClass.equals(Date.class))
				dateFormat = ConversionUtil.getRecentParseFormat();
			
		} catch (Exception e) {
			throw new QueryComposerException("The query '" + query + "' could not be parsed : " + e.getMessage());
		}
	}
	
	public void generateHql(String rootName, Root root) {
		if(foreignKey) {
			
			from += "inner join " + rootName + "." + attributeName + " " + attributeName + " ";

			root.appendFrom(from);
			
			for(Attribute attribute : attributeList) {
				attribute.generateHql(attributeName, root);
			}
			
		} else {
			
			switch(operation) {
				case OP_BETWEEN:
					where += rootName + "." + attributeName + " between :low" + attributeName + " and :high" + attributeName + " ";
				break;
				
				case OP_EMPTY:
					where += rootName + "." + attributeName + " is empty" + " ";
				break;

				case OP_EQUAL:
					where += rootName + "." + attributeName + " = :" + attributeName + " ";
				break;

				case OP_GREATER_OR_EQUAL:
					where += rootName + "." + attributeName + " >= :" + attributeName + " ";
				break;

				case OP_GREATER_THAN:
					where += rootName + "." + attributeName + " > :" + attributeName + " ";
				break;

				case OP_ILIKE:
					where += rootName + "." + attributeName + " not like :" + attributeName + " ";
				break;

				case OP_IN:
					where += rootName + "." + attributeName + " in (:" + attributeName + ") ";
				break;

				case OP_LESS_OR_EQUAL:
					where += rootName + "." + attributeName + " <= :" + attributeName + " ";
				break;

				case OP_LESS_THAN:
					where += rootName + "." + attributeName + " < :" + attributeName + " ";
				break;

				case OP_LIKE:
					where += rootName + "." + attributeName + " like :" + attributeName + " ";
				break;

				case OP_NOT_EMPTY:
					where += rootName + "." + attributeName + " is not empty" + " ";
				break;

				case OP_NOT_EQUAL:
					where += rootName + "." + attributeName + " != :" + attributeName + " ";
				break;

				case OP_NOT_IN:
					where += rootName + "." + attributeName + " not in (:" + attributeName + ") ";
				break;

				case OP_NOT_NULL:
					where += rootName + "." + attributeName + " is not null" + " ";
				break;

				case OP_NULL:
					where += rootName + "." + attributeName + " is null" + " ";
				break;
			}
			
			if(attributeClass.equals(Date.class) && dateFormat!=null) {
				where = where.replaceFirst(rootName + "." + attributeName, "date_format(" + rootName + "." + attributeName + ", '" + dateFormat + "')");
				dateFormat = null;
			}
			
			root.appendWhere(where);
			
		}
	}
	
	public void prepareQuery(Query query) {
		if(foreignKey) {
			
			for(Attribute attribute : attributeList) {
				attribute.prepareQuery(query);
			}
			
		} else {
			
			switch(operation) {
				case OP_BETWEEN:
					query.setParameter("low" + attributeName, getValuesAsList().get(0));
					query.setParameter("high" + attributeName, getValuesAsList().get(1));
				break;
				
				case OP_EMPTY:
					
				break;

				case OP_EQUAL:
					query.setParameter(attributeName, value);
				break;

				case OP_GREATER_OR_EQUAL:
					query.setParameter(attributeName, value);
				break;

				case OP_GREATER_THAN:
					query.setParameter(attributeName, value);
				break;

				case OP_ILIKE:
					query.setParameter(attributeName, value);
				break;

				case OP_IN:
					query.setParameterList(attributeName, getValuesAsList());
				break;

				case OP_LESS_OR_EQUAL:
					query.setParameter(attributeName, value);
				break;

				case OP_LESS_THAN:
					query.setParameter(attributeName, value);
				break;

				case OP_LIKE:
					query.setParameter(attributeName, value);
				break;

				case OP_NOT_EMPTY:
					
				break;

				case OP_NOT_EQUAL:
					query.setParameter(attributeName, value);
				break;

				case OP_NOT_IN:
					query.setParameterList(attributeName, getValuesAsList());
				break;

				case OP_NOT_NULL:
					
				break;

				case OP_NULL:
					
				break;
			}
			
			
		}
	}
	
}
