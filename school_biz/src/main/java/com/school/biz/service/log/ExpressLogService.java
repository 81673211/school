package com.school.biz.service.log;

import java.util.List;
import java.util.Map;

import com.school.biz.dao.log.ExpressLogMapper;
import com.school.biz.domain.entity.express.Express;
import com.school.biz.domain.entity.log.ExpressLog;
import com.school.biz.domain.entity.user.AdminUser;
import com.school.biz.enumeration.ExpressLogActionEnum;
import com.school.biz.service.base.BaseService;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/9/5 15:37
 */
public interface ExpressLogService extends BaseService<ExpressLog, ExpressLogMapper> {

    void log(Express express, ExpressLogActionEnum actionEnum);

    void log(Express express, ExpressLogActionEnum actionEnum, AdminUser adminUser);
    
    List<ExpressLog> queryPage(Map<String, Object> paramMap);
}
