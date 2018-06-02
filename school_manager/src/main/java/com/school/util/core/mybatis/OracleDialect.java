package com.school.util.core.mybatis;

import com.school.util.core.pager.PageInfo;

/**
 * Title: OracleDialect <BR>
 * Description: MyBatis分页插件相关 <BR>
 * Copyright: Copyright (c) 2014-2015<BR>
 * Company: 成都摩宝网络科技有限公司(www.mobo360.com/)<BR>
 * 
 * @author liliang
 * @version 1.0
 */
public class OracleDialect implements Dialect {

	public String getPageSql(String sql, PageInfo page) {
        if (null == page) {
            return sql;
        }
        sql = sql.trim();
        boolean isForUpdate = false;
        if (sql.toLowerCase().endsWith(" for update")) {
            sql = sql.substring(0, sql.length() - 11);
            isForUpdate = true;
        }
        if (page.getNowPage() != -1) {
            StringBuffer pageSql = new StringBuffer();

            pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
            pageSql.append(sql);
            pageSql.append(")  tmp_tb where ROWNUM<=");
            pageSql.append(page.getEndIndex());
            pageSql.append(") where row_id>");
            pageSql.append(page.getStartIndex());
            if (isForUpdate) {
                pageSql.append(" for update");
            }
            return pageSql.toString();
        } else {
            return sql;
        }
    }

}
