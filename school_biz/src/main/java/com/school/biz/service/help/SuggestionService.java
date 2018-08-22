package com.school.biz.service.help;

import java.util.List;
import java.util.Map;

import com.school.biz.domain.entity.help.Suggestion;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/8/20 16:46
 */
public interface SuggestionService {

    void create(Suggestion suggestion);

    List<Suggestion> queryPage(Map<String,Object> paramMap);
}
