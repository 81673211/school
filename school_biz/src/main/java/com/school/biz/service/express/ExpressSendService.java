package com.school.biz.service.express;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.school.biz.dao.express.ExpressSendMapper;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.user.AdminUser;
import com.school.biz.domain.vo.express.ExpressSendVo;
import com.school.biz.exception.ExpressException;
import com.school.biz.service.base.BaseService;

/**
 * @author jame
 */
public interface ExpressSendService extends BaseService<ExpressSend, ExpressSendMapper> {

    /**
     * 创建寄件快件流程
     *
     * @return 返回订单号
     */
    String createSendExpress(ExpressSend expressSend);

    /**
     * 编辑寄件快件
     */
    void modifySendExpress(ExpressSend expressSend);

    /**
     * 通过id获取寄件信息
     *
     * @param id
     * @return
     */
    ExpressSend getSendExpress(Long id);

    /**
     * 修改寄件
     *
     * @param id
     * @param status
     */
    void updateSendExpressStatus(Long id, Integer status);

    /**
     * 寄件列表，通过指定的状态值查询,
     * 默认条件isDelete=0
     *
     * @param status
     * @param openid
     * @return
     * @throws ExpressException
     */
    List<ExpressSend> selectExpressList(Integer[] status, String openid);

    /**
     * 初始化省市区详细内容
     *
     * @param expressSend
     * @return
     */
    ExpressSend initProvinceCityDistrict(ExpressSend expressSend);

    /**
     * 获取寄件实际配送费用
     *
     * @param expressSend
     * @return
     */
    BigDecimal getSendTransportPrice(ExpressSend expressSend);

    //------ from manager -------
    List<ExpressSendVo> queryPage(Map<String, Object> paramMap);

    void saveOrUpdate(ExpressSend expressSend, AdminUser adminUser);

}
