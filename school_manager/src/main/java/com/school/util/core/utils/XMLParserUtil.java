package com.school.util.core.utils;

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

	/**
	 * @author liuhang
	 * @param e 传入的父级element对象
	 * @param element_name element节点名
	 * @return returnElement 返回给定节点名的element子节点
	 */
	public static Element  getChildElement(Element e,String element_name)
	{
		Element returnElement = null;
		List<Element>  el_list = e.elements();
		if(el_list != null && !el_list.isEmpty())
		{
			for(Element tempelement : el_list)
			{
				if(tempelement.getName().equals(element_name))
				{
					returnElement = tempelement;
					break;
				}
			}
		}
		return returnElement;
	}
	public static void main(String[] args) {
		// long start = System.currentTimeMillis();
		String data = "<data a=\"\" b=\"456\">" + "	head	<t0 bbb=\"2\">"
				+ "         dsaf" + "      </t0>"
				+ "      <t1 ><t11>t1111111</t11><t12>t122222222</t12></t1>"
				+ "      <t2>t1111111copy</t2>"
				+ "  dfsafffffffffffffff </data>";

		try {
			Map<String, String> resultMap = new HashMap<String, String>();
			XMLParserUtil.parse(data, resultMap);
			// for (String key : resultMap.keySet()) {
			// }
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
