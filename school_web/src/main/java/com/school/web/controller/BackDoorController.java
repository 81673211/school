package com.school.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.school.web.vo.response.Response;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/9/14 22:57
 */
@Controller
@RequestMapping("/backDoor")
public class BackDoorController {

    @RequestMapping("/918")
    @ResponseBody
    public Response send918() {

        return new Response().writeSuccess("success");
    }
}
