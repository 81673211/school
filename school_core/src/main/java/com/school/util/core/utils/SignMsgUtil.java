/**
 * Title: MOBAO <BR>
 * Description: todo 
 * Copyright: Copyright (c) 2014-2015<BR>
 * Company: 成都摩宝网络科技有限公司(www.mobo360.com/)<BR>
 * 
 * @author linqingsong
 * @version 1.0
 */
package com.school.util.core.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * ClassName: SignMsgUtil
 * @Description:  签名
 * @author linqingsong
 * @date Apr 10, 2015 4:22:24 PM
 */
public class SignMsgUtil {
	
	/**
	 * @Title: getSignMsg 
	 * @Description: 验签
	 * @param @param xml 报文xml
	 * @param @param key key值
	 * @param @return
	 * @param @throws DocumentException
	 * @return String  
	 * @throws
	 */
	public static String getSignMsg(String xml,String key) throws DocumentException{
		Document doc = DocumentHelper.parseText(xml);
		Element rootElt = doc.getRootElement(); // 获取根节点
		Element respData = rootElt.element("respData");
		
		String respXml=respData.asXML();
		respXml=respXml+key;
		String signMsg=MD5Util.getMD5(respXml);
		
		return signMsg;
	}
}
