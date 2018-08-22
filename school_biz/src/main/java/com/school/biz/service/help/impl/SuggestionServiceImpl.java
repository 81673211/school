package com.school.biz.service.help.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.biz.dao.help.SuggestionMapper;
import com.school.biz.domain.entity.help.Suggestion;
import com.school.biz.service.base.impl.BaseServiceImpl;
import com.school.biz.service.help.SuggestionService;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/8/20 16:48
 */
@Service
@Transactional
public class SuggestionServiceImpl extends BaseServiceImpl<Suggestion, SuggestionMapper>
        implements SuggestionService {

    @Override
    public void create(Suggestion suggestion) {
        save(suggestion);
    }

    @Override
    public List<Suggestion> queryPage(Map<String, Object> paramMap) {
        return queryPage(paramMap);
    }
}
