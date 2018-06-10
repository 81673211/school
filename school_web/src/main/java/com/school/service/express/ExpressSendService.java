package com.school.service.express;

import com.school.dao.express.ExpressSendMapper;
import com.school.domain.entity.express.ExpressSend;
import com.school.exception.ExpressException;
import com.school.service.base.BaseService;
import com.school.vo.request.CreateSendExpressVo;

/**
 * @author jame
 */
public interface ExpressSendService extends BaseService<ExpressSend, ExpressSendMapper> {

    /**
     * 创建寄件快件
     *
     * @param expressVo
     * @return
     */
    void createSendExpress(CreateSendExpressVo expressVo) throws ExpressException;
}
