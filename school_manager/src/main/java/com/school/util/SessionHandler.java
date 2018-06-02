package com.school.util;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 方梁 E-mail:fang.liang@yeejen.com
 * @version 创建时间：2015年8月4日 上午10:17:26 限制一个账号在同一时间只能由一人登陆
 */
public class SessionHandler {
	// ConcurrentHashMap 是 Doug Lea 的 util.concurrent 包的一部分，它提供比Hashtable 或者
	// synchronizedMap 更高程度的并发性。而且，对于大多数成功的 get()
	// 操作它会设法避免完全锁定，其结果就是使得并发应用程序有着非常好的吞吐量。
	private static ConcurrentHashMap<String, HttpSession> sessionMap = new ConcurrentHashMap<String, HttpSession>();
	
	//登陆时调用
	public static synchronized void kickUser(String username,HttpSession session){
		HttpSession oldSession = sessionMap.get(username);
		if(oldSession != null){
			if(!oldSession.getId().equals(session.getId())){
				try{
					oldSession.invalidate();
				}catch(Exception e){
					e.getStackTrace();
				}
				sessionMap.put(username, session);
				return;
			}
		}
		sessionMap.put(username, session);
	}
	
	//正常退出时调用
	public static void removeUserFromSessionMap(String username){
		if(sessionMap.containsKey(username))
			sessionMap.remove(username);
	}
}
