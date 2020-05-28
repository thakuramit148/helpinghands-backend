package com.helpinghands.queryhelper;

public class QueryGenerator<T> {

	private final QueryHelper<T> queryHelper = new QueryHelper<>();
	private T t;
	private String tableName;

	public QueryGenerator() {

	}

	public QueryGenerator(String tableName, T t) {
		this.t = t;
		this.tableName = tableName;
	}

	public void setT(T t) {
		this.t = t;
	}

	public T getT() {
		return t;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String generateInsertQuery()
			throws IllegalArgumentException, IllegalAccessException, QueryGeneratorException {
		return queryHelper.insertQueryHelper(tableName, t);
	}

	public String generateInsertQuery(String tableName, T model)
			throws IllegalArgumentException, IllegalAccessException, QueryGeneratorException {
		setTableName(tableName);
		setT(model);
		return queryHelper.insertQueryHelper(tableName, t);
	}
	
	public String generatePreparedStatementInsertQuery(String tableName, T model)
			throws IllegalArgumentException, IllegalAccessException, QueryGeneratorException {
		setTableName(tableName);
		setT(model);
		return queryHelper.insertPreparedStatementQueryHelper(tableName, t);
	}

}