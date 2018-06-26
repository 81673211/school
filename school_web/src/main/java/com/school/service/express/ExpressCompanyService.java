package com.school.service.express;

import com.school.dao.express.ExpressCompanyMapper;
import com.school.domain.entity.express.ExpressCompany;
import com.school.exception.ExpressException;
import com.school.service.base.BaseService;
import com.school.vo.BaseVo;

import java.util.List;

/**
 * @author jame
 */
public interface ExpressCompanyService extends BaseService<ExpressCompany, ExpressCompanyMapper> {

     List<BaseVo> selectList() throws ExpressException;
}
