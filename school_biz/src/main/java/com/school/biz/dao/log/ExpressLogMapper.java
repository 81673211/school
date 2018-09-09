package com.school.biz.dao.log;

import java.util.List;
import java.util.Map;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.domain.entity.log.ExpressLog;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/9/4 22:00
 */
public interface ExpressLogMapper extends BaseDao {

	List<ExpressLog> queryPage(Map<String, Object> paramMap);
}
