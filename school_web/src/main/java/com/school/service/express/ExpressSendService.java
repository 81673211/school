package com.school.service.express;

import com.school.dao.express.ExpressSendMapper;
import com.school.domain.entity.express.ExpressSend;
import com.school.exception.ExpressException;
import com.school.service.base.BaseService;
import com.school.vo.BaseVo;
import com.school.vo.request.SendExpressVo;
import com.school.vo.response.SendExpressResponseVo;

/**
 * @author jame
 */
public interface ExpressSendService extends BaseService<ExpressSend, ExpressSendMapper> {

    /**
     * 创建寄件快件流程
     *
     * @param expressVo
     * @return
     */
    void createSendExpress(SendExpressVo expressVo) throws ExpressException;

    /**
     * 编辑寄件快件
     * @param expressVo
     * @throws ExpressException
     */
    void modifySendExpress(SendExpressVo expressVo) throws ExpressException;

    /**
     * 通过id获取寄件信息
     * @param id
     * @return
     */
    BaseVo getSendExpress(Long id) throws ExpressException;
}
