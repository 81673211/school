package com.school.service.express;

import com.school.dao.express.ExpressSendMapper;
import com.school.domain.entity.express.ExpressSend;
import com.school.exception.ExpressException;
import com.school.service.base.BaseService;
import com.school.vo.BaseVo;
import com.school.vo.request.SendExpressVo;

import java.util.List;

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
     *
     * @param expressVo
     * @throws ExpressException
     */
    void modifySendExpress(SendExpressVo expressVo) throws ExpressException;

    /**
     * 通过id获取寄件信息
     *
     * @param id
     * @return
     */
    BaseVo getSendExpress(Long id) throws ExpressException;

    /**
     * 修改寄件状态
     *
     * @param id
     * @param status
     */
    void updateSendExpressStatus(Long id, Integer status) throws ExpressException;

    /**
     * 寄件列表，通过指定的状态值查询,
     * 默认条件isDelete=0
     *
     * @param status
     * @param phone
     * @return
     * @throws ExpressException
     */
    List<BaseVo> selectExpressList(Integer[] status, String phone) throws ExpressException;
}
