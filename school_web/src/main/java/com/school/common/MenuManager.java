package com.school.common;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.school.service.wechat.MenuService;
import com.school.service.wechat.OauthService;
import com.school.util.wechat.ConstantWeChat;
import com.school.util.wechat.button.Button;
import com.school.util.wechat.button.CommonButton;
import com.school.util.wechat.button.ComplexButton;
import com.school.util.wechat.button.Menu;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 14/06/2018 13:13
 */
@Component
//@Lazy(value = false)
public class MenuManager {

    @Autowired
    private MenuService menuService;

    @Autowired
    private OauthService oauthService;

    @PostConstruct
    public void init() {
        if (ConstantWeChat.REFRESH_MENU) {
            menuService.create(getMenu());
        }
    }

    private Menu getMenu() {
        CommonButton btn11 = new CommonButton();
        btn11.setName("我要寄件");
        btn11.setType("view");
        btn11.setKey("11");
        btn11.setUrl(oauthService.getOAuthUrl("http://www.glove1573.cn/express/send"));

        CommonButton btn12 = new CommonButton();
        btn12.setName("寄件历史");
        btn12.setType("view");
        btn12.setKey("12");
        btn12.setUrl(oauthService.getOAuthUrl("http://www.glove1573.cn/express/send/history"));

        CommonButton btn21 = new CommonButton();
        btn21.setName("待收快件");
        btn21.setType("view");
        btn21.setKey("21");
        btn21.setUrl(oauthService.getOAuthUrl("http://www.glove1573.cn/express/receive"));

        CommonButton btn22 = new CommonButton();
        btn22.setName("收件历史");
        btn22.setType("view");
        btn22.setKey("22");
        btn22.setUrl(oauthService.getOAuthUrl("http://www.glove1573.cn/express/receive/history"));

        CommonButton btn31 = new CommonButton();
        btn31.setName("完善个人信息");
        btn31.setType("view");
        btn31.setUrl(oauthService.getOAuthUrl("http://www.glove1573.cn/customer/profile/edit"));
        btn31.setKey("31");

        CommonButton btn32 = new CommonButton();
        btn32.setName("联系我们");
        btn32.setType("click");
        btn32.setKey("32");

        CommonButton btn33 = new CommonButton();
        btn33.setName("在线帮助");
        btn33.setType("click");
        btn33.setKey("33");

        /**
         * 微信：  mainBtn1,mainBtn2,mainBtn3底部的三个一级菜单。
         */

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("我的寄件");
        //一级下有4个子菜单
        mainBtn1.setSub_button(new Button[] {btn11, btn12});


        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("我的收件");
        mainBtn2.setSub_button(new Button[] {btn21, btn22});


        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("个人中心");
        mainBtn3.setSub_button(new Button[] {btn31, btn32, btn33});


        /**
         * 封装整个菜单
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] {mainBtn1, mainBtn2, mainBtn3});
        return menu;
    }
}
