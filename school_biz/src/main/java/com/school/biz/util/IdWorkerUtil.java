package com.school.biz.util;

import com.school.biz.constant.ConfigProperties;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 09/08/2018 11:05
 */
public final class IdWorkerUtil {

    private static IdWorker idWorker = new IdWorker(ConfigProperties.ID_WORKER_WORK_ID,
                                                          ConfigProperties.ID_WORKER_DATA_ID);

    private IdWorkerUtil() {
    }

    public static IdWorker getSingleton() {
        return idWorker;
    }

    public static String generateOrderNo(String orderType) {
        String orderNo = String.valueOf(idWorker.nextId());
        orderNo = orderType + orderNo;
        return orderNo;
    }
}
