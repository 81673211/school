package com.school.service.express;

import com.school.dao.express.ExpressReceiveMapper;
import com.school.domain.entity.express.ExpressReceive;
import com.school.exception.ExpressException;
import com.school.service.base.BaseService;
import com.school.vo.request.CreateReceiveExpressVo;

/**
 * @author jame
 */
public interface ExpressReceiveService extends BaseService<ExpressReceive, ExpressReceiveMapper> {

    /**
     * 创建收件快件流程
     *
     * @param expressVo
     */
    void createReceiveExpress(CreateReceiveExpressVo expressVo) throws ExpressException;
}
