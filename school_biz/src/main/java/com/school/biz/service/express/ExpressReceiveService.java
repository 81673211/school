package com.school.biz.service.express;

import com.school.biz.dao.express.ExpressReceiveMapper;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.domain.entity.user.AdminUser;
import com.school.biz.domain.vo.PushMessageVo;
import com.school.biz.enumeration.ExpressTypeEnum;
import com.school.biz.enumeration.ReceiveExpressStatusEnum;
import com.school.biz.exception.ExpressException;
import com.school.biz.service.base.BaseService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author jame
 */
public interface ExpressReceiveService extends BaseService<ExpressReceive, ExpressReceiveMapper> {

    /**
     * 创建收件快件流程
     */
    void createReceiveExpress(ExpressReceive expressReceive);

    /**
     * 编辑收件快件
     */
    void modifyReceiveExpress(ExpressReceive expressReceive);

    /**
     * 获取收件快件信息
     *
     * @param id
     * @return
     * @throws ExpressException
     */
    ExpressReceive getReceiveExpress(Long id);

    /**
     * 修改收件状态
     *
     * @param id
     * @param status
     */
    void updateReceiveExpressStatus(Long id, Integer status);

    /**
     * 收件支付-成功之后修改金额以及状态
     *
     * @param expressId
     */
    void updateReceiveExpress(Long expressId);

    /**
     * <b>Description:通过手机号绑定用户ID.</b><br>
     * <b>Author:fred</b>
     * <br><b>Date:01/08/2018 10:56.</b>
     * <br><b>BackLog:</b>
     */
    void bindCustomerByPhone(String phone, Long customerId);

    /**
     * 收件列表，通过指定的状态值查询,
     * 默认条件isDelete=0
     *
     * @param status
     * @param customerId
     * @return
     * @throws ExpressException
     */
    List<ExpressReceive> selectExpressList(Integer[] status, Long customerId);


    /**
     * 初始化省市区详细内容
     *
     * @param expressSend
     * @return
     */
    ExpressReceive initProvinceCityDistrict(ExpressReceive expressSend);

    /**
     * 获取快件对应订单的金额
     *
     * @param expressId
     * @param expressType
     * @return
     */
    BigDecimal getOrderPrice(Long expressId, ExpressTypeEnum expressType);

    //-------- from manager --------
    List<ExpressReceive> queryPage(Map<String, Object> paramMap);

    void saveOrUpdate(ExpressReceive expressReceive, AdminUser adminUser);

    /**
     * 创建帮我取件流程
     *
     * @param expressReceive
     */
    String createHelpReceiveExpress(ExpressReceive expressReceive);

    /**
     * 通过快件状态获取需要推送的消息
     *
     * @param statusEnum
     * @return
     */
    List<PushMessageVo> findPushOpenMessageByExpressStatus(ReceiveExpressStatusEnum statusEnum);

    Integer updateIneffectiveToCancel();

    void updateServiceAmt(BigDecimal payAmount, Long expressId);

}
