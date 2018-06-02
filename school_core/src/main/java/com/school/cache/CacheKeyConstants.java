package com.school.cache;

import com.school.util.core.utils.MD5Util;


/**
 * redis缓存使用的key
 * 
 * @author lidf
 * @version 1.0
 */
public class CacheKeyConstants {
	/**
     * 商品类型
     */
    public static final String schoolGOODTYPEKEY = MD5Util.getMD5("%4_school*OODTYPE_09");
}
