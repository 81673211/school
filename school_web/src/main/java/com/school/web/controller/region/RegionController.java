package com.school.web.controller.region;

import com.school.web.vo.response.DataResponse;
import com.school.web.vo.response.Response;
import com.school.biz.service.region.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jame
 */
@RestController
@RequestMapping(value = "/region")
@Validated
public class RegionController {

    @Autowired
    private RegionService regionService;

    /**
     * 地区映射列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response selectList(@RequestParam(value = "parentId") Long parentId) {
        DataResponse<List> response = new DataResponse<>();
        try {
            List list = regionService.selectRegionList(parentId);
            response.writeSuccess("查询列表成功", list);
        } catch (Exception e) {
            response.writeFailure("查询列表失败");
        }
        return response;
    }
}
