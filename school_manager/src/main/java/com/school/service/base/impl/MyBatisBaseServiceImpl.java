package com.school.service.base.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import com.school.util.core.entity.AbstractEntity;
import com.school.util.core.utils.GenericsUtils;

/**
 * @Description MyBatis Service 基类 公共方法
 * @author
 */
@Transactional
public abstract class MyBatisBaseServiceImpl<M extends AbstractEntity> {
	
	protected static final String SQLNAME_SEPARATOR = ".";
	
	//XmlKey 默认标识符
	protected static final String SQL_SAVE = "save";//增   
	protected static final String SQL_DELETEBYID = "deleteById";//删
	protected static final String SQL_GETBYID = "getById";//查
	protected static final String SQL_UPDATE = "update";//改 
	
	protected static final String SQL_FINDPAGEBY = "findPageBy";   
	protected static final String SQL_FINDLISTBY = "findListBy";
	protected static final String SQL_GETCOUNTBY = "getCountBy";
	
	/**
	 * SqlMapping命名空间
	 */
	private String sqlNamespace = getDefaultSqlNamespace();
	
	/**
	 * myBatis 接口
	 */
	@Autowired
	private SqlSessionTemplate sessionTemplate;
		
	/**
	 * 获取myBatis
	 * @return sqlSessionTemplate
	 */
	protected SqlSessionTemplate getSessionTemplate() {
		
		return sessionTemplate;
	}
	
	/**
	 * 获取默认SqlMapping命名空间。
	 * 使用泛型参数中业务实体类型的全限定名作为默认的命名空间。
	 * 如果实际应用中需要特殊的命名空间，可由子类重写该方法实现自己的命名空间规则。
	 * @return 返回命名空间字符串
	 */
	@SuppressWarnings("unchecked")
	protected String getDefaultSqlNamespace() {
		Class<M> clazz = GenericsUtils.getSuperClassGenricType(getClass(),0);
		String nameSpace = clazz.getName();
		return nameSpace;
	}
	
	/**
	 * 设置SqlMapping命名空间。
	 * 此方法只用于注入SqlMapping命名空间，以改变默认的SqlMapping命名空间，
	 * 不能滥用此方法随意改变SqlMapping命名空间。
	 * @param sqlNamespace SqlMapping命名空间
	 */
	protected void setSqlNamespace(String sqlNamespace) {
		this.sqlNamespace = sqlNamespace;
	}

	/**
	 * 将SqlMapping命名空间与给定的SqlMapping名组合在一起。
	 * @param sqlName SqlMapping名
	 * @return 组合了SqlMapping命名空间后的完整SqlMapping名
	 */
	protected String getSqlName(String sqlName) {
		return this.sqlNamespace + SQLNAME_SEPARATOR + sqlName;
	}
	
	/**
	 * @Description: MyBatis 批量插入
	 * @param xmlKey 
	 * @param list 批量插入对象 
	 * @return 参数值  
	 */
	public int batchInsertMyBatis(String xmlKey, List<?> list) {
		   
		   SqlSession batchSession = getSessionTemplate().getSqlSessionFactory().openSession(ExecutorType.BATCH, false);  
		   int i = 0;  
		   
		   for(int cnt = list.size(); i < cnt; i++) {  
		        
			   batchSession.insert(xmlKey,list.get(i));
		       if((i + 1) % 1000 == 0) {
		          batchSession.flushStatements();  
		       }  
		   }  
		   
		   batchSession.flushStatements();  
		   batchSession.close();  
		        
		   return i;  
	}
	
	/**
	 * @Description: MyBatis 批量更新
	 * @param xmlKey 
	 * @param list 批量更新对象  
	 * @return 参数值  
	 */
	public int batchUpdateMyBatis(String xmlKey, List<?> list) {
		   
		   SqlSession batchSession = getSessionTemplate().getSqlSessionFactory().openSession(ExecutorType.BATCH, false);  
		   int i = 0;  
		   
		   for(int cnt = list.size(); i < cnt; i++) {  
		        
			   batchSession.update(xmlKey,list.get(i));  
		       if((i + 1) % 1000 == 0) {
		          batchSession.flushStatements();  
		       }  
		   }  
		   
		   batchSession.flushStatements();  
		   batchSession.close();  
		        
		   return i;  
	 }  
	 
	/**
	 * @Description: MyBatis 批量删除
	 * @param xmlKey 
	 * @param list 批量删除对象  
	 * @return 参数值  
	 */
	public int batchDeleteMyBatis(String xmlKey, List<?> list) {
	   
		   SqlSession batchSession = getSessionTemplate().getSqlSessionFactory().openSession(ExecutorType.BATCH, false);  
		   int i = 0;  
		   
		   for(int cnt = list.size(); i < cnt; i++) {  
		        
			   batchSession.delete(xmlKey,list.get(i));  
		       if((i + 1) % 1000 == 0) {
		          batchSession.flushStatements();  
		       }  
		   }  
		   
		   batchSession.flushStatements();  
		   batchSession.close();  
		        
		   return i;  
    }  
	 
	/**
	 * @Description: 分页排序构建方法 
	 * @param start 开始行
	 * @param end 结束行
	 * @param paramMap 查询条件
	 * @param orderMap 排序条件 
	 * @return 参数值  
	 */
	protected Map<String,Object> buildParamsWithLimit(int start,int end,Map<String,Object> paramMap,Map<String,Direction>... orderMap){
		
		List<String[]> sortList = new ArrayList<String[]>();
		
		if(orderMap==null||orderMap.length==0||orderMap[0].isEmpty()){
			
			String[] sortMsgArr = new String[2];
			
			sortMsgArr[0] = "id";
			sortMsgArr[1] = "desc";
			
			sortList.add(sortMsgArr);
		}else{
			
			for(Entry<String,Direction> entry : orderMap[0].entrySet()){
				
				String[] sortMsgArr = new String[2];
				
				sortMsgArr[0] = entry.getKey();
				sortMsgArr[1] = entry.getValue().name();
				
				sortList.add(sortMsgArr);
			}
		}
		//排序条件
		paramMap.put("sortList", sortList);
		//分页条件
		paramMap.put("start", start);
		paramMap.put("limit", end - start);
		
		return paramMap;
	}

}
