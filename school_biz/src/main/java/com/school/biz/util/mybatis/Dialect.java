package com.school.biz.util.mybatis;

import com.school.biz.util.pager.PageInfo;

/**
 * Title: Dialect <BR>
 * Description: MyBatis分页插件相关 <BR>
 * Copyright: Copyright (c) 2014-2015<BR>
 * 
 * @author liliang
 * @version 1.0
 */
public interface Dialect {
	
	enum Type {
		MYSQL("mysql"),
		MSSQL("sqlserver"),
		ORACLE("oracle");

        //private final String vendor;

        Type(String vendor) {
		    //this.vendor= vendor;
		}
	}

	String getPageSql(String sql, PageInfo page);
}
