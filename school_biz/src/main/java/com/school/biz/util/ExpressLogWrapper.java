package com.school.biz.util;

import com.school.biz.domain.entity.express.Express;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.log.ExpressLog;
import com.school.biz.enumeration.ExpressTypeEnum;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/9/5 17:24
 */
public final class ExpressLogWrapper {

    private ExpressLogWrapper() {}

    public static ExpressLog wrap(Express express, String action, String status, String remark, Long operatorId, String operatorName) {
        ExpressLog log = new ExpressLog();
        log.setExpressId(express.getId());
        log.setExpressType(getType(express));
        log.setCode(express.getCode());
        log.setStatus(status);
        log.setAction(action);
        log.setRemark(remark);
        log.setOperatorId(operatorId);
        log.setOperatorName(operatorName);
        return log;
    }

    private static int getType(Express express) {
        if (express instanceof ExpressSend) {
            return ExpressTypeEnum.SEND.getFlag();
        } else {
            return ExpressTypeEnum.RECEIVE.getFlag();
        }
    }
}
