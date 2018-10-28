package com.school.biz.service.express;

import java.util.List;

import com.school.biz.dao.express.ExpressReceiveMapper;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.service.base.BaseService;

/**
 * @author jame
 */
public interface ExpressReceiveBatchService extends BaseService<ExpressReceive, ExpressReceiveMapper> {

    List<String> batchCreateReceiveExpress(List<ExpressReceive> expressReceives);
}
