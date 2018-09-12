package com.school.biz.service.supplement.impl;

import com.school.biz.dao.supplement.SupplementMapper;
import com.school.biz.domain.entity.supplement.SupplementInfo;
import com.school.biz.service.supplement.SupplementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class SupplementServiceImpl implements SupplementService {

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
}
