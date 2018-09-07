package com.school.biz.service.log;

import com.school.biz.domain.entity.express.Express;
import com.school.biz.domain.entity.user.AdminUser;
import com.school.biz.enumeration.ExpressLogActionEnum;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/9/5 15:37
 */
public interface ExpressLogService {

    void log(Express express, ExpressLogActionEnum actionEnum);

    void log(Express express, ExpressLogActionEnum actionEnum, AdminUser adminUser);
}
