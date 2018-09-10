package com.school.biz.service.supplement.impl;

import com.school.biz.dao.supplement.SupplementMapper;
import com.school.biz.service.supplement.SupplementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
