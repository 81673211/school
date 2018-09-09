package com.school.biz.dao.help;

import java.util.List;
import java.util.Map;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.domain.entity.help.Suggestion;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/8/20 16:34
 */
public interface SuggestionMapper extends BaseDao {

	List<Suggestion> queryPage(Map<String, Object> paramMap);
}
