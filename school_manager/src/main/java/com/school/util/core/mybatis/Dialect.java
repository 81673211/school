package com.school.util.core.mybatis;

import com.school.util.core.pager.PageInfo;

/**
 * Title: Dialect <BR>
 * Description: MyBatis分页插件相关 <BR>
 * Copyright: Copyright (c) 2014-2015<BR>
 * 
 * @author liliang
 * @version 1.0
 */
public interface Dialect {
	
	public static enum Type {
		MYSQL("mysql"),
		MSSQL("sqlserver"),
		ORACLE("oracle");

        //private final String vendor;

        private Type(String vendor) {
		    //this.vendor= vendor;
		}
	}

	public abstract String getPageSql(String sql, PageInfo page);
}
