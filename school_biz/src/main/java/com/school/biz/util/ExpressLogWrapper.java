package com.school.biz.util;

import com.school.biz.domain.entity.log.ExpressLog;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/9/5 17:24
 */
public final class ExpressLogWrapper {

    private ExpressLogWrapper() {}

    public static ExpressLog wrap(Long expressId, int expressType, String action, String preStatus, String postStatus,
                           String remark, Long operatorId, String operatorName) {
        ExpressLog log = new ExpressLog();
        log.setExpressId(expressId);
        log.setExpressType(expressType);
        log.setAction(action);
        log.setPreStatus(preStatus);
        log.setPostStatus(postStatus);
        log.setRemark(remark);
        log.setOperatorId(operatorId);
        log.setOperatorName(operatorName);
        return log;
    }
}
