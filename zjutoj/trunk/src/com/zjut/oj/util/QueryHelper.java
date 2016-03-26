package com.zjut.oj.util;

import java.util.ArrayList;
import java.util.List;

import com.zjut.oj.common.IDaoSupport;

public class QueryHelper {

	private String fromClause; 
	private String whereClause = "";
	private String orderByClause = "";

	private List<Object> parameters = new ArrayList<Object>();

	public QueryHelper() {
		
	}
	
	@SuppressWarnings("rawtypes")
    public QueryHelper(Class clazz, String alias) {
		fromClause = "FROM " + clazz.getSimpleName() + " " + alias;
	}
	
	@SuppressWarnings("rawtypes")
	public QueryHelper createClass(Class clazz, String alias) {
		if(fromClause == null) {
			fromClause = "FROM " + clazz.getSimpleName() + " " + alias;
		}else {
			fromClause += "," + clazz.getSimpleName() + " " + alias;
		}
		return this;
	}

	public QueryHelper addCondition(String condition, Object... params) {
		if (whereClause.length() == 0) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}

		if (params != null) {
			for (Object p : params) {
				parameters.add(p);
			}
		}

		return this;
	}

	public QueryHelper addCondition(boolean append, String condition, Object... params) {
		if (append) {
			addCondition(condition, params);
		}
		return this;
	}

	public QueryHelper addOrderProperty(String propertyName, boolean asc) {
		if (orderByClause.length() == 0) {
			orderByClause = " ORDER BY " + propertyName + (asc ? " ASC" : " DESC");
		} else {
			orderByClause += ", " + propertyName + (asc ? " ASC" : " DESC");
		}
		return this;
	}

	public QueryHelper addOrderProperty(boolean append, String propertyName, boolean asc) {
		if (append) {
			addOrderProperty(propertyName, asc);
		}
		return this;
	}
	
	public void clear() {
		fromClause = null;
		whereClause = "";
		orderByClause = "";
		parameters.clear();
	}

	public String getListQueryHql() {
		return fromClause + whereClause + orderByClause;
	}

	public String getCountQueryHql() {
		return "SELECT COUNT(*) " + fromClause + whereClause;
	}

	public List<Object> getParameters() {
		return parameters;
	}

	public PageQueryResult pageQuery(IDaoSupport<?> dao, PageQueryResult pageQueryResult) throws ApplicationException {
		pageQueryResult = dao.queryForPage(pageQueryResult, this);
		return pageQueryResult;
	}
	
	@SuppressWarnings("rawtypes")
	public List list(IDaoSupport<?> dao) throws ApplicationException {
		List list = dao.queryForList(this);
		return list;
	}
}
