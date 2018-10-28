package com.school.biz.service.express.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.school.biz.dao.express.ExpressCompanyMapper;
import com.school.biz.dao.express.ExpressReceiveMapper;
import com.school.biz.domain.entity.customer.Customer;
import com.school.biz.domain.entity.express.ExpressCompany;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.enumeration.ReceiveExpressStatusEnum;
import com.school.biz.enumeration.ReceiveExpressTypeEnum;
import com.school.biz.service.base.impl.BaseServiceImpl;
import com.school.biz.service.customer.CustomerService;
import com.school.biz.service.express.ExpressReceiveBatchService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jame
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpressReceiveBatchServiceImpl extends BaseServiceImpl<ExpressReceive, ExpressReceiveMapper>
        implements ExpressReceiveBatchService {

    @Autowired
    private ExpressCompanyMapper expressCompanyMapper;
    @Autowired
    private ExpressReceiveMapper expressReceiveMapper;
    @Autowired
    private CustomerService customerService;

    @Override
    public List<String> batchCreateReceiveExpress(List<ExpressReceive> expressReceives) {
        List<String> checkResult = beforeCreateCheck(expressReceives);
        batchCreate(expressReceives, checkResult);
        return checkResult;
    }

    private void batchCreate(List<ExpressReceive> expressReceives, List<String> checkInfo) {
        if (CollectionUtils.isEmpty(expressReceives)) {
            checkInfo.add("录入快件数量：0");
            return;
        }
        boxCompany(expressReceives);
        for (ExpressReceive expressReceive : expressReceives) {
            boxCustomer(expressReceive);
            expressReceive.setExpressStatus(ReceiveExpressStatusEnum.PROXY_RECIEVED.getFlag());
            expressReceive.setExpressType(ReceiveExpressTypeEnum.PROXY_RECEIVE.getFlag());
            expressReceive.setExpressWeight(BigDecimal.ONE);
        }
        try {
            int insertCount = expressReceiveMapper.batchInsert(expressReceives);
            checkInfo.add("录入快件数量：" + insertCount);
        } catch (Exception e) {
            checkInfo.add("录入快件时出现错误：" + e.getMessage());
        }
    }

    private void boxCompany(List<ExpressReceive> expressReceives) {
        String companyCode = expressReceives.get(0).getCompanyCode();
        ExpressCompany company = expressCompanyMapper.findByCode(companyCode);
        Long companyId = company.getId();
        String companyName = company.getName();
        for (ExpressReceive expressReceive : expressReceives) {
            expressReceive.setCompanyId(companyId);
            expressReceive.setCompanyName(companyName);
        }
    }

    private void boxCustomer(ExpressReceive expressReceive) {
        Customer customer = customerService.getByPhone(expressReceive.getReceiverPhone());
        if (customer != null) {
            expressReceive.setCustomerId(customer.getId());
            expressReceive.setReceiverName(customer.getName());
            expressReceive.setReceiverPhone(customer.getPhone());
            expressReceive.setReceiverAddr(customer.getAddr());
        }
    }

    private List<String> beforeCreateCheck(List<ExpressReceive> expressReceives) {
        List<String> results = new ArrayList<>();
        if (CollectionUtils.isEmpty(expressReceives)) {
            results.add("待录入快件总数：0");
            return results;
        }
        int totalCount = expressReceives.size();
        results.add("待录入快件总数：" + totalCount);
        List<String> codes = new ArrayList<>();
        for (ExpressReceive expressReceive : expressReceives) {
            codes.add(expressReceive.getCode());
        }
        long count = expressReceiveMapper.count(codes);
        if (count > 0) {
            List<ExpressReceive> receives = expressReceiveMapper.list(codes);
            Set<String> codeSet = new HashSet<>();
            for (ExpressReceive receive : receives) {
                results.add("已存在的快递单号：" + receive.getCode());
                codeSet.add(receive.getCode());
            }
            for (int i = expressReceives.size() - 1; i >= 0; i--) {
                ExpressReceive receive = expressReceives.get(i);
                if (codeSet.contains(receive.getCode())) {
                    expressReceives.remove(i);
                }
            }
        }
        return results;
    }
}
