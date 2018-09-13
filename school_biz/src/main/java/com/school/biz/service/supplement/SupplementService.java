package com.school.biz.service.supplement;

import com.school.biz.domain.entity.supplement.SupplementInfo;
import com.school.biz.enumeration.ExpressTypeEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jame
 * @date 2018/9/10
 * @desc description
 */
public interface SupplementService {
    List<SupplementInfo> selectByIds(List ids);

    void updateIsPay(Long id);

    boolean checkIsPayOff(Long expressId);

    List<SupplementInfo> selectNotPayByExpress(Long expressId, Integer expressType, Integer type);
}
