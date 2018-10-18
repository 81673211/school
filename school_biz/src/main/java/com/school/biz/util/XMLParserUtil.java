package com.school.biz.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * ClassName: MsgInfoDao 
 * @Description: 解析xml
 * @author linqingsong
 * @date Mar 31, 2015
 */
@SuppressWarnings("unchecked")
public class XMLParserUtil {
	/**
	 * 解析xml数据，将xml数据解析好后放入map中，目前只支持元素名不重复的xml数据 取数据时直接中map中根据key值取值。
	 * key采用元素路径的方式.元素用类似/root/element/data的路径形式，属性值用root/element/data/@id的形式，id是属性名称。
	 * 
	 * @param xmlData
	 * @throws Exception
	 */
	protected static final Log logger = LogFactory.getLog(XMLParserUtil.class);

	public static void parse(String xmlData, Map<String, String> resultMap)
			throws Exception {
		try {
			org.dom4j.Document doc = DocumentHelper.parseText(xmlData);
			Element root = doc.getRootElement();// 指向根节点
			parseNode(root, resultMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 递归方式解析所有数据
	 * 
	 * @param node
	 *            节点
	 * @param resultMap
	 *            存放结果的map
	 * @throws Exception
	 */
	private static void parseNode(Element node, Map<String, String> resultMap)
			throws Exception {
		try {
			List attList = node.attributes();
			List eleList = node.elements();
			for (int i = 0; i < attList.size(); i++) {
				Attribute att = (Attribute) attList.get(i);
				resultMap.put(att.getPath(), att.getText().trim());
			}
			resultMap.put(node.getPath(), node.getText().trim());
			for (int i = 0; i < eleList.size(); i++) {
				parseNode((Element) eleList.get(i), resultMap);
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
