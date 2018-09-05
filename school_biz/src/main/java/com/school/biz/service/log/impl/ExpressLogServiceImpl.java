package com.school.biz.service.log.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.biz.dao.log.ExpressLogMapper;
import com.school.biz.domain.entity.log.ExpressLog;
import com.school.biz.service.base.impl.BaseServiceImpl;
import com.school.biz.service.log.ExpressLogService;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/9/5 17:19
 */
@Service
@Transactional
public class ExpressLogServiceImpl extends BaseServiceImpl<ExpressLog, ExpressLogMapper>
        implements ExpressLogService {


    @Override
    public void create(ExpressLog expressLog) {
        save(expressLog);
    }
}
