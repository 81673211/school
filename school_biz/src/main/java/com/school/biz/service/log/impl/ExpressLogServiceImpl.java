package com.school.biz.service.log.impl;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.school.biz.component.ExpressLogOperatorHelper;
import com.school.biz.dao.log.ExpressLogMapper;
import com.school.biz.domain.entity.express.Express;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.log.ExpressLog;
import com.school.biz.domain.entity.user.AdminUser;
import com.school.biz.enumeration.ExpressLogActionEnum;
import com.school.biz.enumeration.ReceiveExpressStatusEnum;
import com.school.biz.enumeration.SendExpressStatusEnum;
import com.school.biz.service.base.impl.BaseServiceImpl;
import com.school.biz.service.log.ExpressLogService;
import com.school.biz.util.ExpressLogWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/9/5 17:19
 */
@Service
@Transactional
@Slf4j
public class ExpressLogServiceImpl extends BaseServiceImpl<ExpressLog, ExpressLogMapper>
        implements ExpressLogService {

    @Autowired
    private ExpressLogOperatorHelper expressLogOperatorHelper;

    @Override
    public void log(Express express, ExpressLogActionEnum actionEnum) {
        String statusName = getStatusName(express);
        if (statusName == null) {
            return;
        }
        logCustomer(express, statusName, actionEnum);
    }

    @Override
    public void log(Express express, ExpressLogActionEnum actionEnum, AdminUser adminUser) {
        if (adminUser == null) {
            log.error("记录操作日志：管理后台登陆用户为空");
            return;
        }
        String statusName = getStatusName(express);
        if (statusName == null) {
            return;
        }
        logBack(express, statusName, actionEnum, adminUser);
    }

    private String getStatusName(Express express) {
        String statusName;
        Integer status = express.getExpressStatus();
        if (express instanceof ExpressSend) {
            SendExpressStatusEnum sendExpressStatusEnum = SendExpressStatusEnum.parseObj(status);
            if (sendExpressStatusEnum == null) {
                log.error("记录操作日志：未识别的寄件状态，status:{}", status);
                return null;
            } else {
                statusName = sendExpressStatusEnum.getMessage();
            }
        } else if (express instanceof ExpressReceive) {
            ReceiveExpressStatusEnum receiveExpressStatusEnum = ReceiveExpressStatusEnum.parseObj(status);
            if (receiveExpressStatusEnum == null) {
                log.error("记录操作日志：未识别的收件状态，status:{}", status);
                return null;
            } else {
                statusName = receiveExpressStatusEnum.getMessage();
            }
        } else {
            log.warn("记录操作日志：未识别的快件类型，class:{}", express.getClass());
            return null;
        }
        return statusName;
    }

    private void logBack(Express express, String statusName, ExpressLogActionEnum actionEnum, AdminUser adminUser) {
        CompletableFuture.runAsync(() -> {
            try {
                save(ExpressLogWrapper.wrap(express, actionEnum.getMsg(), statusName,
                                            JSON.toJSONString(express), adminUser.getId(), adminUser.getAdminName()));
            } catch (Exception e) {
                log.error("记录操作日志：管理后台保存失败，{}", e.getMessage());
            }
        });
    }

    private void logCustomer(Express express, String statusName, ExpressLogActionEnum actionEnum) {
        CompletableFuture.runAsync(() -> {
            try {
                Long customerId = express.getCustomerId();
                String customerOperatorName = expressLogOperatorHelper.getCustomerOperatorName(customerId);
                save(ExpressLogWrapper.wrap(express, actionEnum.getMsg(), statusName, JSON.toJSONString(express), customerId, customerOperatorName));
            } catch (Exception e) {
                log.error("记录操作日志：前台保存失败，{}", e.getMessage());
            }
        });
    }
}
