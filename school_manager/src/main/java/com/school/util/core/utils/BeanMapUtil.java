package com.school.util.core.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanMapUtil
{
  public static Object Map2Bean(Class type, Map map)
    throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException
  {
    
    BeanInfo beanInfo = Introspector.getBeanInfo(type);

    Object obj = type.newInstance();

    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

    for (int i = 0; i < propertyDescriptors.length; ++i) {
      PropertyDescriptor descriptor = propertyDescriptors[i];
      String propertyName = descriptor.getName();
      if (!(map.containsKey(propertyName.toUpperCase()))) continue;
      try {
        Object value = map.get(propertyName.toUpperCase());
        BeanUtils.setProperty(obj, propertyName, value);
      }
      catch (Exception e) {
      }
    }
    return obj;
  }

  public static Map bean2Map(Object bean)
    throws IntrospectionException, IllegalAccessException, InvocationTargetException
  {
    Class type = bean.getClass();
    Map returnMap = new HashMap();
    BeanInfo beanInfo = Introspector.getBeanInfo(type);

    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
    for (int i = 0; i < propertyDescriptors.length; ++i) {
      PropertyDescriptor descriptor = propertyDescriptors[i];
      String propertyName = descriptor.getName();
      if (!(propertyName.equals("class"))) {
        Method readMethod = descriptor.getReadMethod();
        Object result = readMethod.invoke(bean, new Object[0]);
        if (result != null)
          returnMap.put(propertyName, result);
        else {
          returnMap.put(propertyName, "");
        }
      }
    }
    return returnMap;
  }
}