package com.school.util.core.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * 用于对象的数据复制 只支持java类型的对象的间的复制   自定义对象的话  需要扩展  很简单  的   自己
 * @author yiwenxiang
 *
 */
public class Bean2Bean {
	private static String[] types = { "java.lang.Integer", "java.lang.Double",
		"java.lang.Float", "java.lang.Long","java.math.BigDecimal", "java.lang.Short",
		"java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
		"java.lang.String", "int", "double", "long", "short", "byte",
		"boolean", "char", "float" };
	public static void copy(Object src,Object des){
		try {
			BeanInfo beanInfosrc = Introspector.getBeanInfo(src.getClass());
			BeanInfo beanInfodes = Introspector.getBeanInfo(des.getClass());
			PropertyDescriptor[] proDescrtptorssrc = beanInfosrc.getPropertyDescriptors();
			PropertyDescriptor[] proDescrtptorsdes = beanInfodes.getPropertyDescriptors();
			
			if ((proDescrtptorssrc != null && proDescrtptorssrc.length > 0) && (proDescrtptorsdes != null && proDescrtptorsdes.length > 0)) {
				for (PropertyDescriptor propsrc : proDescrtptorssrc) {
					if (propsrc.getName().contains("class")) {
						continue;
					}
					if(!contains(propsrc.getPropertyType().getName())){
						continue;
					}
					for (PropertyDescriptor propdes : proDescrtptorsdes) {
						if (propdes.getName().contains("class")) {
							continue;
						}
						if(!contains(propdes.getPropertyType().getName())){
							continue;
						}
						if(propsrc.getName().equals(propdes.getName())){
							Object value = propsrc.getReadMethod().invoke(src, null);
							propdes.getWriteMethod().invoke(des,value);
						}
					}
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
	}
	
	private static boolean contains(String name){
		for(String key:types){
			if(key.equals(name)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 自定义对象的复制和扩展  
	 * @param src
	 * @param des
	 */
	public static void copyExtend(Object src,Object des){
		
	}
}
