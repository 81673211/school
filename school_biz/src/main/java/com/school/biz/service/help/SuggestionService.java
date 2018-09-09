package com.school.biz.service.help;

import java.util.List;
import java.util.Map;

import com.school.biz.dao.help.SuggestionMapper;
import com.school.biz.domain.entity.help.Suggestion;
import com.school.biz.service.base.BaseService;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/8/20 16:46
 */
public interface SuggestionService extends BaseService<Suggestion, SuggestionMapper> {

    void create(Suggestion suggestion);

    List<Suggestion> queryPage(Map<String,Object> paramMap);
}
