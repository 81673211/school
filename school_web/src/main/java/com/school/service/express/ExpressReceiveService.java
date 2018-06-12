package com.school.service.express;

import com.school.dao.express.ExpressReceiveMapper;
import com.school.domain.entity.express.ExpressReceive;
import com.school.exception.ExpressException;
import com.school.service.base.BaseService;
import com.school.vo.BaseVo;
import com.school.vo.request.ReceiveExpressVo;
import com.school.vo.response.ReceiveExpressResponseVo;

/**
 * @author jame
 */
public interface ExpressReceiveService extends BaseService<ExpressReceive, ExpressReceiveMapper> {

    /**
     * 创建收件快件流程
     *
     * @param expressVo
     */
    void createReceiveExpress(ReceiveExpressVo expressVo) throws ExpressException;

    /**
     * 编辑收件快件
     * @param expressVo
     * @throws ExpressException
     */
    void modifyReceiveExpress(ReceiveExpressVo expressVo) throws ExpressException;

    /**
     * 获取收件快件信息
     * @param id
     * @return
     * @throws ExpressException
     */
    BaseVo getReceiveExpress(Long id) throws ExpressException;
}
