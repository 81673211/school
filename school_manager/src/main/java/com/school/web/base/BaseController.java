/**
 * Title: MOBAO <BR>
 * Description: todo Copyright: Copyright (c) 2014-2015<BR>
 * Company: 成都摩宝网络科技有限公司(www.mobo360.com/)<BR>
 *
 * @author linqingsong
 * @version 1.0
 */
package com.school.web.base;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;

import com.school.util.core.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: BaseController 
 * @Description: TODO
 * @author linqingsong
 * @date Feb 16, 2015
 */
@Slf4j
@Controller
public class BaseController {

//	@Autowired
//	private Validator validator;

    /**
     * @Description: 默认成功
     * @author fujie
     */
    protected static final Map<String, Object> SUCCESS;

    /**
     * @Description: 默认失败
     * @author fujie
     */
    protected static final Map<String, Object> ERROR;

    static {
        SUCCESS = success();
        ERROR = error();
    }

    /**
     * @Description: 返回成功结果数据
     * @param  obj 返回数据对象
     * @author fujie
     */
    protected static Map<String, Object> success(Object obj) {

        Map<String, Object> successMap = new HashMap<String, Object>();

        successMap.put("code", "0");
        successMap.put("msg", "成功");
        successMap.put("data", obj);

        return successMap;
    }

    /**
     * @Description: 返回默认失败 结果数据
     * @author fujie
     */
    protected static Map<String, Object> error() {

        return error(BusinessException.createErr(String.valueOf("5000")));
    }

    /**
     * @Description: 返回成功结果数据和分页信息
     * @param obj 返回数据对象
     * @param totalPage 总共有多少页
     * @param totalRecordNum  总共有多少条记录数
     * @author fujie
     */
    protected static Map<String, Object> success(Object obj, long totalPage, long totalRecordNum) {

        Map<String, Object> successMap = new HashMap<String, Object>();

        successMap.put("code", "0");
        successMap.put("msg", "成功");
        successMap.put("data", obj);

        Map<String, Long> pageMap = new HashMap<String, Long>();
        pageMap.put("totalPage", totalPage);
        pageMap.put("totalRecordNum", totalRecordNum);

        successMap.put("page", pageMap);

        return successMap;
    }

    /**
     * @Description: 数据操作成功
     * @author fujie
     */
    private static Map<String, Object> success() {

        Map<String, Object> successMap = new HashMap<String, Object>();

        successMap.put("code", "0");
        successMap.put("msg", "成功");

        return successMap;
    }

    protected static Map<String, Object> error(BusinessException e) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("code", e.getCode());
        errorMap.put("msg", e.getMsg());
        return errorMap;
    }

    protected static Map<String, Object> error(int errCode) {

        return error(BusinessException.createErr(String.valueOf(errCode)));
    }

    /**
     * @Description: JSR 303 验证失败错误封装信息
     * @author fujie
     */
    protected static Map<String, Object> error(Map<String, String> errorMap) {

        Map<String, Object> map = new HashMap<String, Object>();

        String errorMsg = "";
        for (String key : errorMap.keySet()) {
            errorMsg += errorMap.get(key) + ";";
        }

        map.put("code", "4999");
        map.put("msg", errorMsg.substring(0, errorMsg.lastIndexOf(";")));

        return map;
    }

}
 