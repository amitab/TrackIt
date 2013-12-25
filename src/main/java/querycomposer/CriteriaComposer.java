package querycomposer;

import helper.AbstractModelObject;
import helper.ConversionUtil;
import helper.PojoToTable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;

import dao.HibernateUtil;

public class CriteriaComposer extends AbstractModelObject {
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
	
	//Specific to Front End
	private String readableCriteriaName;
	private String readableOperation;
	
	private String criteriaName;
	private java.lang.Class<?> criteriaClass;
	private List<CriteriaComposer> criteriaList = new ArrayList<CriteriaComposer>();
	
	private boolean entity;
	private boolean root;
	
	private java.lang.reflect.Field Criteriafield;
	private String query;
	private Object value;
	private int operation;
	
	// Query Specfic
	private static String where = "";
	private static String from = "";
	private static Query preparedQuery;
	
	// Date specific
	private String dateFormat = null;
	
	// Entity Specific
	private String[] attributeNames;
	
	// Front End Operation Names Specific
	public static String[] operationNames() {
		
		String[] operationNames = {
				"EQUAL TO",
				"NOT EQUAL TO",
				"LESS THAN",
				"GREATER THAN",
				"LESS THAN OR EQUAL TO",
				"GREATER THAN OR EQUAL TO",
				"LIKE",
				"NOT LIKE",
				"IN",
				"NOT IN",
				"NULL",
				"NOT NULL",
				"EMPTY",
				"NOT EMPTY",
				"BETWEEN"
		};
		
		return operationNames;
	}
	
	public String operationName() {
		return readableOperation;
	}
	
	// Confirm
	
	public boolean isEntity() {
		return entity;
	}
	
	public boolean isRoot() {
		return root;
	}
	
	// Constructors
	public CriteriaComposer() {
		
	}
	
	public CriteriaComposer(String criteriaName) {
		this.criteriaName = criteriaName;
		this.entity = true;
		try {
			this.criteriaClass = Class.forName("dto." + criteriaName);
			root = true;
			this.readableCriteriaName = criteriaName;
		} catch (ClassNotFoundException e) {
			root = false;
			this.readableCriteriaName = ConversionUtil.toReadable(criteriaName);
		}
	}
	
	public CriteriaComposer(String criteriaName, String query) {
		this.criteriaName = criteriaName;
		this.query = query;
		this.entity = false;
		this.operation = OP_EQUAL;
		this.readableOperation = CriteriaComposer.operationNames()[operation];
		this.readableCriteriaName = ConversionUtil.toReadable(criteriaName);
	}
	
	public CriteriaComposer(String criteriaName, String query, int operation) {
		this.criteriaName = criteriaName;
		this.entity = false;
		this.query = query;
		this.operation = operation;
		this.readableOperation = CriteriaComposer.operationNames()[operation];
		this.readableCriteriaName = ConversionUtil.toReadable(criteriaName);
	}
	
	public CriteriaComposer(String criteriaName, int operation) {
		this.criteriaName = criteriaName;
		this.entity = false;
		this.operation = operation;
		this.readableOperation = CriteriaComposer.operationNames()[operation];
		this.readableCriteriaName = ConversionUtil.toReadable(criteriaName);
	}
	
	// Static Instantiators
	
	public static CriteriaComposer newCriteria(String criteriaName) {
		if(criteriaName.trim().equalsIgnoreCase("") || criteriaName == null) {
			return null;
		}
		return new CriteriaComposer(criteriaName);
	}

	public static CriteriaComposer newCriteria(String criteriaName, String query) {
		if(criteriaName.trim().equalsIgnoreCase("") || criteriaName == null) {
			return null;
		}
		return new CriteriaComposer(criteriaName, query);
	}
	
	public static CriteriaComposer newCriteria(String criteriaName, String query, int operation) {
		if(criteriaName.trim().equalsIgnoreCase("") || criteriaName == null) {
			return null;
		}
		return new CriteriaComposer(criteriaName, query, operation);
	}
	
	public static CriteriaComposer newCriteria(String criteriaName, int operation) {
		if(criteriaName.trim().equalsIgnoreCase("") || criteriaName == null) {
			return null;
		}
		return new CriteriaComposer(criteriaName, operation);
	}
	
	// setters and getters
	
	public String[] getAttributeNames() {
		return attributeNames;
	}

	public void setAttributeNames(String[] attributeNames) {
		this.attributeNames = attributeNames;
	}
	
	public String getReadableCriteriaName() {
		return readableCriteriaName;
	}

	public void setReadableCriteriaName(String readableCriteriaName) {
		String oldValue = this.readableCriteriaName;
		this.readableCriteriaName = readableCriteriaName;
		this.criteriaName = ConversionUtil.fromReadable(readableCriteriaName, this.root);
		firePropertyChange("readableCriteriaName", oldValue, readableCriteriaName);
	}
	
	public int getOperation() {
		return operation;
	}
	
	public void setOperation(int operation) {
		this.operation = operation;
		this.readableOperation = CriteriaComposer.operationNames()[operation];
	}
	
	public Object getValue() {
		return value;
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
	
	public CriteriaComposer addCriteria(CriteriaComposer criteria) {
		if(criteria != null) {
			criteriaList.add(criteria);
		}
		return this;
	}
	
	public CriteriaComposer addCriterias(CriteriaComposer ...criterias) {
		for(CriteriaComposer criteria : criterias) {
			if(criteria != null) {
				criteriaList.add(criteria);
			}
		}
		return this;
	}
	
	public List<CriteriaComposer> getCriteriaList() {
		return criteriaList;
	}
	
	public String getCriteriaName() {
		return criteriaName;
	}
	
	public void setCriteriaName(String criteriaName) {
		String oldValue = this.criteriaName;
		this.criteriaName = criteriaName;
		this.readableCriteriaName = ConversionUtil.toReadable(criteriaName);
		firePropertyChange("criteriaName", oldValue, criteriaName);
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		String oldValue = this.query;
		this.query = query;
		firePropertyChange("query", oldValue, query);
	}
	
	public java.lang.Class<?> getCriteriaClass() {
		return criteriaClass;
	}
	
	public static void appendFrom(String addfrom) {
		from += addfrom + "\n";
	}
	
	public static String getFromQuery() {
		return from;
	}

	public static void appendWhere(String addwhere) {
		if(where.equalsIgnoreCase("where "))
			where += addwhere + "\n";
		else
			where += "and " + addwhere + "\n";
	}
	
	public String getWhereQuery() {
		return where;
	}
	
	// Custom
	
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
				
				value = ConversionUtil.convertIfNeeded(splitString[0], criteriaClass);
				
			} else {
				
				if(operation != OP_IN || operation != OP_NOT_IN || operation != OP_BETWEEN ) {
					throw new QueryComposerException("Operation " + operation + " must have only 1 argument.");
				}
				
				if(operation == OP_BETWEEN ) {
					throw new QueryComposerException("Operation " + operation + " must have 2 arguments.");
				}
				
				for(String string : splitString) {
					Object data = string.trim();
					data = ConversionUtil.convertIfNeeded(data, criteriaClass);
					dataObjects.add(data);
				}
				
				value = dataObjects;
			}
			
			// If there was a date conversion, get the format
			if(criteriaClass.equals(Date.class))
				dateFormat = ConversionUtil.getRecentParseFormat();
			
		} catch (Exception e) {
			throw new QueryComposerException("The query '" + query + "' could not be parsed : " + e.getMessage());
		}
	}
	
	public void compileRelations(CriteriaComposer rootCriteria) throws QueryComposerException {
		java.lang.Class<?> rootCriteriaClass = null;
		
		try {
			
			if(!root && entity) {
				rootCriteriaClass = rootCriteria.getCriteriaClass();
				Criteriafield = rootCriteriaClass.getDeclaredField(criteriaName);
				
				annotation.AssociationType associationType = Criteriafield.getAnnotation(annotation.AssociationType.class);
				if(associationType != null) {
					
					criteriaClass = Class.forName("dto." + associationType.type());
					
				} else {
					throw new QueryComposerException("Could not find Association : " + criteriaName);
				}
				
			} if (entity) {
				
				attributeNames = PojoToTable.getColumnsOfClass(criteriaClass);
				for(CriteriaComposer criteria : criteriaList) {
					criteria.compileRelations(this);
				}
				
			}
			
		} catch (Exception e) {
			throw new QueryComposerException("Could not compile criteria : " + criteriaName + " : " + e.getMessage());
		} 
	}
	
	private void processCriteria(CriteriaComposer rootCriteria) throws QueryComposerException {
		java.lang.Class<?> rootCriteriaClass = null;
		String rootCriteriaName = null;
		if(!root) {
			rootCriteriaClass = rootCriteria.getCriteriaClass();
			rootCriteriaName = rootCriteria.getCriteriaName();
		}

		try {
			if(!root) {
				
				Criteriafield = rootCriteriaClass.getDeclaredField(criteriaName);
				if(entity) {
					annotation.AssociationType associationType = Criteriafield.getAnnotation(annotation.AssociationType.class);
					if(associationType != null) {
						criteriaClass = Class.forName("dto." + associationType.type());
						String from = "inner join " + rootCriteriaClass.getSimpleName() + "." + criteriaName + " " + criteriaName + " ";
						CriteriaComposer.appendFrom(from);
					} else {
						throw new QueryComposerException("Could not find Association : " + criteriaName);
					}
					
				} else {
					criteriaClass = Criteriafield.getType();
					processQuery();
					String where = "";
					switch(operation) {
						case OP_BETWEEN:
							where += rootCriteriaName + "." + criteriaName + " between :low" + criteriaName + " and :high" + criteriaName + " ";
						break;
						
						case OP_EMPTY:
							where += rootCriteriaName + "." + criteriaName + " is empty" + " ";
						break;
	
						case OP_EQUAL:
							where += rootCriteriaName + "." + criteriaName + " = :" + criteriaName + " ";
						break;
	
						case OP_GREATER_OR_EQUAL:
							where += rootCriteriaName + "." + criteriaName + " >= :" + criteriaName + " ";
						break;
	
						case OP_GREATER_THAN:
							where += rootCriteriaName + "." + criteriaName + " > :" + criteriaName + " ";
						break;
	
						case OP_ILIKE:
							where += rootCriteriaName + "." + criteriaName + " not like :" + criteriaName + " ";
						break;
	
						case OP_IN:
							where += rootCriteriaName + "." + criteriaName + " in (:" + criteriaName + ") ";
						break;
	
						case OP_LESS_OR_EQUAL:
							where += rootCriteriaName + "." + criteriaName + " <= :" + criteriaName + " ";
						break;
	
						case OP_LESS_THAN:
							where += rootCriteriaName + "." + criteriaName + " < :" + criteriaName + " ";
						break;
	
						case OP_LIKE:
							where += rootCriteriaName + "." + criteriaName + " like :" + criteriaName + " ";
						break;
	
						case OP_NOT_EMPTY:
							where += rootCriteriaName + "." + criteriaName + " is not empty" + " ";
						break;
	
						case OP_NOT_EQUAL:
							where += rootCriteriaName + "." + criteriaName + " != :" + criteriaName + " ";
						break;
	
						case OP_NOT_IN:
							where += rootCriteriaName + "." + criteriaName + " not in (:" + criteriaName + ") ";
						break;
	
						case OP_NOT_NULL:
							where += rootCriteriaName + "." + criteriaName + " is not null" + " ";
						break;
	
						case OP_NULL:
							where += rootCriteriaName + "." + criteriaName + " is null" + " ";
						break;
					}
					
					if(criteriaClass.equals(Date.class) && dateFormat!=null) {
						where = where.replaceFirst(rootCriteriaName + "." + criteriaName,
								"date_format(" + rootCriteriaName + "." + criteriaName + ", '" + dateFormat + "')");
						dateFormat = null;
					}
					
					CriteriaComposer.appendWhere(where);
				}
			} else {
				CriteriaComposer.from += criteriaName + " from " + criteriaName + " " + criteriaName + " \n";
				CriteriaComposer.where += "where ";
			}
			
			if (entity) {
				for(CriteriaComposer criteria : criteriaList) {
					criteria.processCriteria(this);
				}
			}
			
		} catch (Exception e) {
			throw new QueryComposerException("Could not process criteria : " + criteriaName + " : " + e.getMessage());
		} 
	}
	
	public String generateHql() throws QueryComposerException {
		if(this.root) {
			clean(this);
			if(where.trim().equalsIgnoreCase("") || from.trim().equalsIgnoreCase("")) {
				processCriteria(null);
			}
			return from + where;
		}
		return null;
	}
	
	private void prepareQuery() throws QueryComposerException {
		
		 if (!root && !entity) {
			switch(operation) {
				case OP_BETWEEN:
					CriteriaComposer.preparedQuery.setParameter("low" + criteriaName, getValuesAsList().get(0));
					CriteriaComposer.preparedQuery.setParameter("high" + criteriaName, getValuesAsList().get(1));
				break;
	
				case OP_IN:
	
				case OP_NOT_IN:
					CriteriaComposer.preparedQuery.setParameterList(criteriaName, getValuesAsList());
				break;
	
				case OP_EQUAL:
	
				case OP_GREATER_OR_EQUAL:
	
				case OP_GREATER_THAN:
	
				case OP_ILIKE:
					
				case OP_LESS_OR_EQUAL:
	
				case OP_LESS_THAN:
	
				case OP_LIKE:
	
				case OP_NOT_EQUAL:
					CriteriaComposer.preparedQuery.setParameter(criteriaName, value);
				break;
			}
		} 
		
		if(root || entity) {
			
			if(root) {
				try {
					CriteriaComposer.preparedQuery = HibernateUtil.getSession().createQuery(generateHql());
				} catch (Exception e) {
					throw new QueryComposerException(e.getMessage());
				}
			}
			
			for(CriteriaComposer criteria : criteriaList) {
				criteria.prepareQuery();
			}
		}
		
	}
	
	public CriteriaComposer distinct() {
		CriteriaComposer.from = "distinct " + CriteriaComposer.from;
		return this;
	}
	
	public List<?> list() throws QueryComposerException {
		CriteriaComposer.from = "select " + CriteriaComposer.from;
		prepareQuery();
		CriteriaComposer.from = "";
		CriteriaComposer.where = "";
		return CriteriaComposer.preparedQuery.list();
	}
	
	public void clean(CriteriaComposer rootCriteria) {
		Set<CriteriaComposer> removalList = new HashSet<CriteriaComposer>();
		for(CriteriaComposer c : rootCriteria.getCriteriaList()) {
			clean(c);
			if(c.getCriteriaList().size() == 0 && c.isEntity()) {
				removalList.add(c);
			} 
		}
		rootCriteria.getCriteriaList().removeAll(removalList);
	}
	
	public void deleteAll() {
		criteriaList.clear();
	}
	
}
