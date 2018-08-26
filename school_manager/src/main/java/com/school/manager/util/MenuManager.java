package com.school.manager.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.ConfigProperties;
import com.school.biz.service.wechat.MenuService;
import com.school.biz.service.wechat.OauthService;
import com.school.biz.domain.bo.wechat.Button;
import com.school.biz.domain.bo.wechat.CommonButton;
import com.school.biz.domain.bo.wechat.ComplexButton;
import com.school.biz.domain.bo.wechat.Menu;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 14/06/2018 13:13
 */
@Component
//@Lazy(value = false)
@Slf4j
public class MenuManager {

    @Autowired
    private MenuService menuService;

    @Autowired
    private OauthService oauthService;

    @PostConstruct
    public void init() {
        if (ConfigProperties.REFRESH_MENU) {
            menuService.create(getMenu());
        }
        log.info("menu:{}", JSON.toJSONString(menuService.get()));
    }

    private Menu getMenu() {
        CommonButton btn11 = new CommonButton();
        btn11.setName("我要寄件");
        btn11.setType("view");
        btn11.setKey("11");
        btn11.setUrl(oauthService.getOAuthUrl("/express/sending"));

        CommonButton btn12 = new CommonButton();
        btn12.setName("寄件历史");
        btn12.setType("view");
        btn12.setKey("12");
        btn12.setUrl(oauthService.getOAuthUrl("/express/0/list"));

        CommonButton btn21 = new CommonButton();
        btn21.setName("待收快件");
        btn21.setType("view");
        btn21.setKey("21");
        btn21.setUrl(oauthService.getOAuthUrl("/express/1/list?status=0,1,2,3,4"));

        CommonButton btn22 = new CommonButton();
        btn22.setName("收件历史");
        btn22.setType("view");
        btn22.setKey("22");
        btn22.setUrl(oauthService.getOAuthUrl("/express/1/list?status=5"));

        CommonButton btn23 = new CommonButton();
        btn23.setName("帮我取件");
        btn23.setType("view");
        btn23.setKey("23");
        btn23.setUrl(oauthService.getOAuthUrl("/abc"));

        CommonButton btn31 = new CommonButton();
        btn31.setName("个人信息");
        btn31.setType("view");
        btn31.setUrl(oauthService.getOAuthUrl("/customer/profile"));
        btn31.setKey("31");

        CommonButton btn32 = new CommonButton();
        btn32.setName("联系我们");
        btn32.setType("view");
        btn32.setUrl("http://www.glove1573.cn/contact_us.html");
        btn32.setKey("32");

        CommonButton btn33 = new CommonButton();
        btn33.setName("意见及建议");
        btn33.setType("view");
        btn33.setUrl(oauthService.getOAuthUrl("/help/suggestion"));
        btn33.setKey("33");

        /**
         * 微信：  mainBtn1,mainBtn2,mainBtn3底部的三个一级菜单。
         */

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("代我寄件");
        mainBtn1.setSub_button(new Button[] {btn11, btn12});


        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("我要取件");
        mainBtn2.setSub_button(new Button[] {btn21, btn22, btn23 });


        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("个人中心");
        mainBtn3.setSub_button(new Button[] {btn31, btn32, btn33 });


        /**
         * 封装整个菜单
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] {mainBtn1, mainBtn2, mainBtn3});
        return menu;
    }
}
