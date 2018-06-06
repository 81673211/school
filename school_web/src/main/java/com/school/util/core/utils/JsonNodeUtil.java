package com.school.util.core.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * JsonNode工具类
 * @author Administrator
 *
 */
public class JsonNodeUtil {

	
	/**
	 * 将jsonNode解析为list
	 * @param jsonNode
	 * @param nodeName
	 * @return
	 */
	public static List<Object> parseToList(JsonNode jsonNode,String nodeName){
		ObjectMapper objectMapper = new ObjectMapper();
		List<Object> nodeList = null;
		try {
			 nodeList = objectMapper.treeToValue(jsonNode.findPath(nodeName), List.class);
		} catch (Exception e) {
			return null;
		}
		return nodeList;
	}
	
	/**
	 * 将jsonNode解析为map
	 * @param jsonNode
	 * @param nodeName
	 * @return
	 */
	public static Map parseToMap(JsonNode jsonNode,String nodeName){
		ObjectMapper objectMapper = new ObjectMapper();
		Map nodeMap = null;
		try {
			nodeMap = objectMapper.treeToValue(jsonNode.findPath(nodeName), Map.class);
		} catch (Exception e) {
			return null;
		}
		return nodeMap;
	}
	
	/**
	 * 将jsonNode解析为map
	 * @param jsonNode
	 * @param nodeName
	 * @return
	 */
	public static Map parseToMap(JsonNode jsonNode){
		ObjectMapper objectMapper = new ObjectMapper();
		Map nodeMap = null;
		try {
			nodeMap = objectMapper.treeToValue(jsonNode, Map.class);
		} catch (Exception e) {
			return null;
		}
		return nodeMap;
	}
	
	/**
	 * 将jsonNode解析为String
	 * @param jsonNode
	 * @param nodeName
	 * @return
	 */
	public static String parseToString(JsonNode jsonNode,String nodeName){
		try {
			JsonNode node = jsonNode.findPath(nodeName);
			return node.asText();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * 将jsonNode解析为Integer
	 * @param jsonNode
	 * @param nodeName
	 * @return
	 */
	public static int parseToInteger(JsonNode jsonNode,String nodeName){
		try {
			JsonNode node = jsonNode.findPath(nodeName);
			return node.asInt();
		} catch (Exception e) {
			return 0;
		}
		
	}
	/**
	 * 将jsonNode解析为小数BigDecimal
	 * @param jsonNode
	 * @param nodeName
	 * @return
	 */
	public static BigDecimal parseToBigDecimal(JsonNode jsonNode,String nodeName){
		try {
			JsonNode node = jsonNode.findPath(nodeName);
			return node.decimalValue();
		} catch (Exception e) {
			return  new BigDecimal("0.00");
		}
		
	}
	
	/**
	 * 将Object解析为Json树
	 * @param obj Java对象
	 * @return ObjectNode
	 */
	public static ObjectNode parseObjectToTree(Object obj){
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			 
			 String json = objectMapper.writeValueAsString(obj);
			 
			 ObjectNode node = (ObjectNode)objectMapper.readTree(json);
			 
			 return node;
			 
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 将Object解析为JSon字符串
	 * @param obj Java对象
	 * @return String
	 */
	public static String parseObjectToJson(Object obj){
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			 
			 String json = objectMapper.writeValueAsString(obj);
			 
			 return json;
			 
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Long parseToLong(JsonNode jsonNode,String nodeName){
		try {
			JsonNode node = jsonNode.findPath(nodeName);
			return node.asLong();
		} catch (Exception e) {
			return null;
		}
		
	}
}
