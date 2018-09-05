package com.school.biz.dao.box;

import java.util.List;
import java.util.Map;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.domain.entity.box.ExpressBoxInfo;

public interface ExpressBoxInfoMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(ExpressBoxInfo record);

    int insertSelective(ExpressBoxInfo record);

    ExpressBoxInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExpressBoxInfo record);

    int updateByPrimaryKey(ExpressBoxInfo record);

	List<ExpressBoxInfo> queryPage(Map<String, Object> paramMap);
}