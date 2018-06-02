package com.school.util.core.mybatis;

import com.school.util.core.pager.PageInfo;

/**
 * Title: MySqlDialect <BR>
 * Description: MyBatis分页插件相关 <BR>
 * Copyright: Copyright (c) 2014-2015<BR>
 * 
 * @author liliang
 * @version 1.0
 */
public class MySqlDialect implements Dialect {

	public String getPageSql(String sql, int offset, int limit) {
		return getMySQLPageSql(sql, offset, limit);
	}

	public String getPageSql(String sql, PageInfo page) {
	    if (page.getNowPage() != -1) {
	        return getMySQLPageSql(sql, page.getStartIndex(), page.getEndIndex()-page.getStartIndex());
	    } else {
	        return sql;
	    }
	}

	/**
	 * 得到分页的SQL
	 * 
	 * @param offset
	 *            偏移量
	 * @param limit
	 *            位置
	 * @return 分页SQL
	 */
	private String getMySQLPageSql(String querySelect, int offset, int limit) {

		querySelect = getLineSql(querySelect);

		String sql = querySelect + " limit "
				+ offset + " ," + limit;

		return sql;
	}
	
	/**
	 * 将SQL语句变成一条语句，并且每个单词的间隔都是1个空格
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 如果sql是NULL返回空，否则返回转化后的SQL
	 */
	private static String getLineSql(String sql) {
		return sql.replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ");
	}

}
