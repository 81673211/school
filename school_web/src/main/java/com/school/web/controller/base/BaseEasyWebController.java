package com.school.web.controller.base;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.school.web.vo.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import java.util.Date;


/**
 * @author fujie
 * @Description: web端基础类---专注用于MyBatis体系
 */

@Controller
@Slf4j
@SuppressWarnings("all")
public abstract class BaseEasyWebController {

    protected final int HTTP_SUCCESS = 200;
    protected static String dateFormat;

    public static final String PARAM_ERROR = "参数错误";

    static {
        dateFormat = "yyyy-MM-dd HH:mm:ss";
        SerializeConfig mapping = new SerializeConfig();
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
    }


    /**
     * @param bindingResult
     * @return
     * @Validated校验
     */
    public void checkValid(BindingResult bindingResult, Response response) {
        if (bindingResult.hasErrors()) {
            response.writeFailure(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            response.writeSuccess();
        }
    }

}
