package com.helpinghands.queryhelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.helpinghands.queryhelper.annotations.QueryHelperColumnName;
import com.helpinghands.queryhelper.annotations.QueryHelperNullValue;
import com.helpinghands.queryhelper.annotations.QueryHelperPrimaryKey;

public class QueryHelper<T> {

	List<Field[]> mainList;

	public QueryHelper() {
	}

	public String insertQueryHelper(String tableName, T model) throws IllegalArgumentException, IllegalAccessException {
		if (model == null) {
			throw new QueryGeneratorException("Error in parsing: given model object is not initialized");
		} else if (tableName == null) {
			throw new QueryGeneratorException("Error in parsing: please supply a `table name`");
		}
		mainList = new ArrayList<>();
		getFields(model.getClass());
		StringBuffer columnNames = new StringBuffer();
		StringBuffer columnValues = new StringBuffer();
		List<Field> l = mainList.stream().flatMap(Arrays::stream).collect(Collectors.toList());
		final int length = l.size();
		for (int i = 0; i < length; i++) {
			l.get(i).setAccessible(true);
			Object value = l.get(i).get(model);
			if (value == null) {
				throw new QueryGeneratorException("Error in parsing: " + l.get(i).getName() + " ");
			}
			if (!l.get(i).isAnnotationPresent(QueryHelperPrimaryKey.class)) {
				if (l.get(i).isAnnotationPresent(QueryHelperColumnName.class)) {
					QueryHelperColumnName c = l.get(i).getAnnotation(QueryHelperColumnName.class);
					columnNames.append("`").append(c.name()).append("`").append((i < length - 1) ? ", " : " ");
				} else {
					columnNames.append("`").append(l.get(i).getName()).append("`")
							.append((i < length - 1) ? ", " : " ");
				}
				if (checkDatatype(l.get(i))) {
					columnValues.append("'").append(value).append((i < length - 1) ? "', " : "'");
				} else {
					columnValues.append(value).append((i < length - 1) ? ", " : "");
				}
			}
		}
		String sql = String.format("INSERT INTO `%s` (%s) values(%s)", tableName, columnNames.toString(),
				columnValues.toString());
		return sql;
	}

	public String insertPreparedStatementQueryHelper(String tableName, T model)
			throws IllegalArgumentException, IllegalAccessException {
		if (model == null) {
			throw new QueryGeneratorException("Error in parsing: given model object is not initialized");
		} else if (tableName == null) {
			throw new QueryGeneratorException("Error in parsing: please supply a `table name`");
		}
		mainList = new ArrayList<>();
		getFields(model.getClass());
		StringBuffer columnNames = new StringBuffer();
		StringBuffer columnValues = new StringBuffer();
		List<Field> l = mainList.stream().flatMap(Arrays::stream).collect(Collectors.toList());
		final int length = l.size();
		for (int i = 0; i < length; i++) {
			l.get(i).setAccessible(true);
			Object value = l.get(i).get(model);
			final String name = l.get(i).getName();
			if (!l.get(i).isAnnotationPresent(QueryHelperNullValue.class) && value == null) {
				throw new QueryGeneratorException("Error in parsing: " + l.get(i).getName() + " ");
			}
			if (!l.get(i).isAnnotationPresent(QueryHelperPrimaryKey.class)) {
				if (l.get(i).isAnnotationPresent(QueryHelperColumnName.class)) {
					QueryHelperColumnName c = l.get(i).getAnnotation(QueryHelperColumnName.class);
					columnNames.append("`").append(c.name()).append("`").append((i < length - 1) ? ", " : " ");
				} else {
					columnNames.append("`").append(name).append("`").append((i < length - 1) ? ", " : " ");
				}
				if(l.get(i).isAnnotationPresent(QueryHelperNullValue.class))
				{
					columnValues.append("NULL").append((i < length - 1) ? ", " : "");
				}else {
					columnValues.append(":").append(name).append((i < length - 1) ? ", " : "");
				}			
			}
		}
		String sql = String.format("INSERT INTO `%s` (%s) values(%s)", tableName, columnNames.toString(),
				columnValues.toString());
		return sql;
	}

	public boolean checkDatatype(Field field) {
		final String type = field.getType().getName();
		return (!type.equals("int") && !type.equals("float") && !type.equals("double") && !type.equals("long")
				&& !type.equals("boolean"));
	}

	public List<Field[]> getFields(Class<?> c) {
		if (c.getDeclaredFields().length > 0) {
			mainList.add(c.getDeclaredFields());
		}
		if (c.getSuperclass() != null) {
			return getFields(c.getSuperclass());
		}
		return mainList;
	}
}
