package com.school.service.security;

import com.school.dao.security.MenuInfoMapper;
import com.school.domain.entity.security.MenuInfo;
import com.school.domain.vo.security.LeftMenu;
import com.school.service.base.BaseService;

import java.util.List;
import java.util.Map;

/** 
 * @author  作者：方梁
 * @date 创建时间：2016年12月12日 下午3:57:30
 * @description   
 */
public interface MenuInfoService extends BaseService<MenuInfo, MenuInfoMapper> {
    
    /**
     * 
     * 方法描述：获取我的左侧菜单
     * <br>创建人：方梁
     * <br>创建时间：2016年12月13日 下午4:25:20
     * <br>最后修改人：方梁
     * <br>最后修改时间：2016年12月13日 下午4:25:20
     * <br>修改备注：
     * <br>@version 1.0
     */
    public List<LeftMenu> getMyLeftMenus(Map<String, Object> map);

	public List<MenuInfo> getAll();

	/**
	 * 根据传入的menuId获取包含的菜单
	 */
	public List<Long> getOwnMenuIdList(Object menuIdObj);
}
