package com.school.service.express;

import com.school.dao.express.ExpressReceiveMapper;
import com.school.domain.entity.express.ExpressReceive;
import com.school.exception.ExpressException;
import com.school.service.base.BaseService;
import com.school.vo.BaseVo;
import com.school.vo.request.ReceiveExpressCreateVo;
import com.school.vo.request.ReceiveExpressModifyVo;

import java.util.List;

/**
 * @author jame
 */
public interface ExpressReceiveService extends BaseService<ExpressReceive, ExpressReceiveMapper> {

    /**
     * 创建收件快件流程
     *
     * @param expressVo
     */
    void createReceiveExpress(ReceiveExpressCreateVo expressVo) throws ExpressException;

    /**
     * 编辑收件快件
     *
     * @param expressVo
     * @throws ExpressException
     */
    void modifyReceiveExpress(ReceiveExpressModifyVo expressVo) throws ExpressException;

    /**
     * 获取收件快件信息
     *
     * @param id
     * @return
     * @throws ExpressException
     */
    BaseVo getReceiveExpress(Long id) throws ExpressException;

    /**
     * 修改收件状态
     *
     * @param id
     * @param status
     */
    void updateReceiveExpressStatus(Long id, Integer status) throws ExpressException;

    /**
     * 收件列表，通过指定的状态值查询,
     * 默认条件isDelete=0
     *
     * @param status
     * @param phone
     * @return
     * @throws ExpressException
     */
    List<BaseVo> selectExpressList(Integer[] status, String phone) throws ExpressException;
}
