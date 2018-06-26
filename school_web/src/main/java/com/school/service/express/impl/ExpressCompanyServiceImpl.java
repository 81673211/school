package com.school.service.express.impl;

import com.school.dao.express.ExpressCompanyMapper;
import com.school.domain.entity.express.ExpressCompany;
import com.school.exception.ExpressException;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.express.ExpressCompanyService;
import com.school.util.core.Log;
import com.school.vo.BaseVo;
import com.school.vo.response.ExpressCompanyListResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author jame
 */
@Service
public class ExpressCompanyServiceImpl extends BaseServiceImpl<ExpressCompany, ExpressCompanyMapper> implements ExpressCompanyService {

    @Autowired
    private ExpressCompanyMapper companyMapper;

    @Override
    public List<BaseVo> selectList() throws ExpressException {
        List<BaseVo> result = new ArrayList<>();
        try {
            List<ExpressCompany> list = companyMapper.selectByParams(new HashMap<>());
            for (ExpressCompany expressCompany : list) {
                result.add(converterPo2Vo(expressCompany, new ExpressCompanyListResponseVo()));
            }
        } catch (Exception e) {
            String msg = "throw exception when get express company list";
            Log.error.error(msg, e);
            throw new ExpressException(msg, e);
        }
        return result;
    }
}
