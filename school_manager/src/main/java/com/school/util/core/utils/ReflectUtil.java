package com.school.util.core.utils;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ReflectUtil {
	private static Logger log = Logger.getLogger(ReflectUtil.class); 
	/**
	 * 将from的值拷贝给to
	 * @param from
	 * @param to
	 * @return
	 * @throws Exception
	 */
	public static void copy(Object from, Object to) throws Exception {
		Class<?> fromClass = from.getClass();
		Class<?> toClass = to.getClass();
		log.info("from["+fromClass.getName()+"]to["+toClass.getName()+"]");
		// 获得对象的所有成员变量
		Field[] fields = fromClass.getDeclaredFields();
		for (Field field : fields) {
			// 获取成员变量的名字
			String name = field.getName(); // 获取成员变量的名字，此处为id，name,age
			log.info("from["+fromClass.getName()+"]to["+toClass.getName()+"]fieldName["+name+"]");
			Field toField = null;
			try{
				toField = toClass.getDeclaredField(name);
			} catch(Exception e) {
			}
			
			if(toField == null) {
				log.info("from["+fromClass.getName()+"]to["+toClass.getName()+"]fieldName["+name+"] not need copy");
				continue;
			}

			// 获取get和set方法的名字
			String firstLetter = name.substring(0, 1).toUpperCase(); // 将属性的首字母转换为大写
			String getMethodName = "get" + firstLetter + name.substring(1);
			String setMethodName = "set" + firstLetter + name.substring(1);
			// System.out.println(getMethodName + "," + setMethodName);
			
			// 获取方法对象
			Method getMethod = fromClass.getMethod(getMethodName,
					new Class[] {});
			Method setMethod = toClass.getMethod(setMethodName,
					new Class[] { toField.getType() });// 注意set方法需要传入参数类型

			// 调用get方法获取旧的对象的值
			Object value = getMethod.invoke(from, new Object[] {});
			// 调用set方法将这个值复制到新的对象中去
			setMethod.invoke(to, new Object[] { value });
			log.info("from["+fromClass.getName()+"]to["+toClass.getName()+"]fieldName["+name+"] copy success");
		}
	}
	
	public Object copy(Object object) throws Exception
    {
        Class<?> classType = object.getClass();


        /* 生成新的对象的讨论
        // 获得Constructor对象,此处获取第一个无参数的构造方法的
        Constructor cons = classType.getConstructor(new Class[] {});//不带参数，所以传入一个为空的数组
        // 通过构造方法来生成一个对象
        Object obj = cons.newInstance(new Object[] {});

        // 以上两行代码等价于：
        Object obj11 = classType.newInstance();  // 这行代码无法处理构造函数有参数的情况
        
        //用第二个带参数的构造方法生成对象
        Constructor cons2 = classType.getConstructor(new Class[] {String.class, int.class});
        Object obj2 = cons2.newInstance(new Object[] {"ZhangSan",20});
        
        */
        
        Object objectCopy = classType.getConstructor(new Class[]{}).newInstance(new Object[]{});
        
        //获得对象的所有成员变量
        Field[] fields = classType.getDeclaredFields();
        for(Field field : fields)
        {
            //获取成员变量的名字
            String name = field.getName();    //获取成员变量的名字，此处为id，name,age
            //System.out.println(name);

            //获取get和set方法的名字
            String firstLetter = name.substring(0,1).toUpperCase();    //将属性的首字母转换为大写            
            String getMethodName = "get" + firstLetter + name.substring(1);
            String setMethodName = "set" + firstLetter + name.substring(1);            
            //System.out.println(getMethodName + "," + setMethodName);
            
            //获取方法对象
            Method getMethod = classType.getMethod(getMethodName, new Class[]{});
            Method setMethod = classType.getMethod(setMethodName, new Class[]{field.getType()});//注意set方法需要传入参数类型
            
            //调用get方法获取旧的对象的值
            Object value = getMethod.invoke(object, new Object[]{});
            //调用set方法将这个值复制到新的对象中去
            setMethod.invoke(objectCopy, new Object[]{value});

        }

        return objectCopy;

    }
	
	/**
	 * 将from的值拷贝给to
	 * @param from
	 * @param to
	 * @param excludeFields 需要排除的字段
	 * @throws Exception
	 */
	public static void copy(Object from, Object to,String...excludeFields) throws Exception {
		Class<?> fromClass = from.getClass();
		Class<?> toClass = to.getClass();
		log.info("from["+fromClass.getName()+"]to["+toClass.getName()+"]");
		// 获得对象的所有成员变量
		Field[] fields = fromClass.getDeclaredFields();
		for (Field field : fields) {
			// 获取成员变量的名字
			String name = field.getName(); // 获取成员变量的名字，此处为id，name,age
			List<String> fieldList = Arrays.asList(excludeFields);
			if(fieldList.contains(name)){
				continue;
			}
			log.info("from["+fromClass.getName()+"]to["+toClass.getName()+"]fieldName["+name+"]");
			Field toField = null;
			try{
				toField = toClass.getDeclaredField(name);
			} catch(Exception e) {
			}
			
			if(toField == null) {
				log.info("from["+fromClass.getName()+"]to["+toClass.getName()+"]fieldName["+name+"] not need copy");
				continue;
			}

			// 获取get和set方法的名字
			String firstLetter = name.substring(0, 1).toUpperCase(); // 将属性的首字母转换为大写
			String getMethodName = "get" + firstLetter + name.substring(1);
			String setMethodName = "set" + firstLetter + name.substring(1);
			// System.out.println(getMethodName + "," + setMethodName);
			
			// 获取方法对象
			Method getMethod = fromClass.getMethod(getMethodName,
					new Class[] {});
			Method setMethod = toClass.getMethod(setMethodName,
					new Class[] { toField.getType() });// 注意set方法需要传入参数类型

			// 调用get方法获取旧的对象的值
			Object value = getMethod.invoke(from, new Object[] {});
			// 调用set方法将这个值复制到新的对象中去
			setMethod.invoke(to, new Object[] { value });
			log.info("from["+fromClass.getName()+"]to["+toClass.getName()+"]fieldName["+name+"] copy success");
		}
	}
}
