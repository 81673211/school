package com.school.web.controller.help;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.school.biz.constant.RedisKeyNS;
import com.school.biz.domain.entity.help.Suggestion;
import com.school.biz.service.help.SuggestionService;
import com.school.web.vo.response.Response;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/8/20 16:50
 */
@Controller
@RequestMapping("/help/suggestion")
public class SuggestionController {

    @Autowired
    private SuggestionService suggestionService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "openId") String openId) {
        ModelAndView mav = new ModelAndView("suggestion");
        mav.addObject("openId", openId);
        return mav;
    }

    @RequestMapping(value = "/push", method = RequestMethod.POST)
    @ResponseBody
    public Response create(@RequestParam("openId") String openId, @RequestParam("content") String content) {
        if (StringUtils.isBlank(content) || content.length() > 300) {
            return new Response().writeFailure("填写内容不能为空或大于300个字");
        }
        String cacheKey = RedisKeyNS.CACHE_NAMESPACE_SUGGESTION + openId;
        String cacheVal = redisTemplate.opsForValue().get(cacheKey);
        if (Objects.equals(cacheVal, "1")) {
            return new Response().writeFailure("一分钟内请不要重复提交");
        }
        Suggestion suggestion = new Suggestion();
        suggestion.setOpenId(openId);
        suggestion.setContent(content);
        suggestionService.create(suggestion);
        redisTemplate.opsForValue().set(cacheKey, "1", 60, TimeUnit.SECONDS);
        return new Response().writeSuccess();
    }
}
