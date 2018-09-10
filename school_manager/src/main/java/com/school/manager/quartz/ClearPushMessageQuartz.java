package com.school.manager.quartz;

import com.school.biz.service.express.ExpressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 未支付快递消息推送：快递创建后，10分钟内还未支付成功则推送消息给用户
 */
@Slf4j
@Service(value = "ClearPushMessageQuartz")
public class ClearPushMessageQuartz {

    @Autowired
    private ExpressService expressService;

    public void execute() throws Exception {
        log.info("==========NotExpressQuartz：开始==========");
        Integer count = expressService.cleanPushMessageAndCancelExpress();
        log.info("==========NotExpressQuartz：已完成==========IneffectiveToCancel size:" + count);
    }

}
