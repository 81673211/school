package com.school.biz.service.supplement.impl;

import com.school.biz.dao.order.RefundOrderInfoMapper;
import com.school.biz.dao.supplement.SupplementMapper;
import com.school.biz.domain.entity.order.RefundOrderInfo;
import com.school.biz.domain.entity.supplement.SupplementInfo;
import com.school.biz.service.base.BaseService;
import com.school.biz.service.base.impl.BaseServiceImpl;
import com.school.biz.service.supplement.SupplementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jame
 * @date 2018/9/10
 * @desc description
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SupplementServiceImpl extends BaseServiceImpl<SupplementInfo, SupplementMapper> implements SupplementService {

    @Autowired
    private SupplementMapper supplementMapper;

    @Override
    public List<SupplementInfo> selectByIds(List ids) {
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        return supplementMapper.selectByParams(map);
    }

    @Override
    public void updateIsPay(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("isPay", 1);
        supplementMapper.updateByPrimaryKeySelective(map);
    }

    @Override
    public boolean checkIsPayOff(Long expressId) {
        Map<String, Object> map = new HashMap<>();
        map.put("expressId", expressId);
        map.put("isPay", 0);
        return supplementMapper.selectByParams(map).isEmpty();
    }

    @Override
    public List<SupplementInfo> selectNotPayByExpress(Long expressId, Integer expressType) {
        Map<String, Object> map = new HashMap<>();
        map.put("expressId", expressId);
        map.put("expressType", expressType);
        map.put("isPay", false);
        return supplementMapper.selectByParams(map);
    }

    @Override
    public BigDecimal getNotPayAmout(Long expressId, Integer expressType) {
        BigDecimal amount = BigDecimal.ZERO;
        List<SupplementInfo> supplementInfos = selectNotPayByExpress(expressId, expressType);
        if (!CollectionUtils.isEmpty(supplementInfos)) {
            for (SupplementInfo supplementInfo : supplementInfos) {
                amount = amount.add(supplementInfo.getAmount());
            }
        }
        return amount;
    }
}
