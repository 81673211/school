package com.school.util.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.util.core.exception.HttpExcetpion;
import com.school.util.core.pojo.ReqEntity;
import com.school.util.core.pojo.ReqHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
/**
 * 用于调用基础服务应用的工具
 * @author fujie
 *
 */
public class ApiPost {
	
	private static final Logger LOG = LoggerFactory.getLogger(ApiPost.class);
	
	//head
	private static ReqHeader header = null;
	
	private static String posturl;
	//默认请求数量
	private static int pagesize = 10;
	//默认页码
	private static int pagenum = 1;

	//创建请求业务对象
	protected static <S> ReqEntity buildReqEntity(S s){
		
		//自定义请求实体
		ReqEntity<S> req = new ReqEntity<S>();
		
		if(header==null){
			
			header = new ReqHeader();
			header.setPagesize(pagesize);
			header.setPagenum(pagenum);
			header.setOs("android");
			//测试
			//String token = "UClRZgPdqy+c85AbBfcTwjN4yUx+EvmP5B7ADTXLfAF0VH8Wau0ELKpAf2JT hqgB+mGMm9E3k3ybrAy4K2qgoS4A5pdHAN9YiG2fb1HY1S/CoApxvKfjwiAw FC5yeUW5Fu91vROd5fCozonjo5UBOZQj4AksT6VL7LwLtBv3bc4=";
			//开发
			String token = "GdxlGUWavtoAeLFdPFdscgnwo7r5fhpJ+XcjtgwjrrvnNljh1DiEaoaN9D3F LyG6Z0QtrEN8nsgGeY0EtVXqlstXL5iQGCW8EWKwx/ALQeFCB5NAj+gGSxkk xGiFtZGCjXXytUKgctH7u7RflRi0kJwz/MXBTK5T4v/e0T3+0Eo=";
			header.setToken(token);
		}
		
		req.setHeader(header);
		
		//body
		req.setBody(s);
		
		return req;
	} 
	
	public static void setHeader(ReqHeader header){
		ApiPost.header = header;
	}
	
	//创建请求体
	protected static <T> HttpEntity<T> getRequestEntity(T type){
		
		return new HttpEntity(type,getHeaders());
	} 
	
	//创建请求头
	protected static HttpHeaders getHeaders(){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		try {
			headers.setLocation(new URI(posturl));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return headers;
	}
	
	//创建RestTemplate模板
	protected static RestTemplate bulidRestTemplate(){
		
		RestTemplate restTemplate = new RestTemplate();
		//json
		MappingJackson2HttpMessageConverter json = new MappingJackson2HttpMessageConverter();
		//xml
		//......
		
		restTemplate.getMessageConverters().add(json);
		
		return restTemplate;
	}

	
	/**
	 * 发送Post请求
	 * @param url
	 * @param s
	 * @return
	 * @throws HttpExcetpion
	 */
	public static <S> Map sendPost(String url,S s) throws HttpExcetpion {
		
		LOG.info("服务调用,请求地址:"+url);
		
		posturl = url;
		
		ReqEntity entity = buildReqEntity(s);
		
		ResponseEntity<Map> responseEntity = null;
		
		try {
			
			responseEntity =  bulidRestTemplate().exchange(url, HttpMethod.POST, getRequestEntity(entity), Map.class);
		
		}catch(Exception e){
			
			LOG.error("sendPost error", e);
			
			throw new HttpExcetpion(4998);
		}
		
		reset();
		
		return responseEntity.getBody();
	}
	
	//发送Post请求
	public static <S> Map sendPost(String url,PageRequest pageRequest,S s) throws HttpExcetpion {
		
		LOG.info("服务调用,请求地址:"+url);
		
		posturl = url;
		
		pagesize = pageRequest.getPageSize();
		
		pagenum = pageRequest.getPageNumber();
		
		ReqEntity entity = buildReqEntity(s);
		
		ResponseEntity<Map> responseEntity  = null;
		
		try{
		
			 responseEntity =  bulidRestTemplate().exchange(url, HttpMethod.POST, getRequestEntity(entity), Map.class);
		
		}catch(Exception e){
			
			LOG.info("sendPost error", e);
			
			throw new HttpExcetpion(4998);
		}
		
		reset();
		
		return responseEntity.getBody();
	}
	
	/**
	 * 发送Post请求ForTree
	 * @param url
	 * @param pageRequest
	 * @param s
	 * @return
	 */
	public static <S> JsonNode sendPostForTree(String url,PageRequest pageRequest,S s) throws HttpExcetpion {
		pagesize = pageRequest.getPageSize();
		pagenum = pageRequest.getPageNumber();
		return sendPostForTree(url, s);
	}
	
	/**
	 * 发送Post请求ForTree
	 * @param url
	 * @param s
	 * @return
	 */
	public static <S> JsonNode sendPostForTree(String url,S s) throws HttpExcetpion {
		
		LOG.info("服务调用,请求地址:"+url);
		
		posturl = url;
		
		ReqEntity entity = buildReqEntity(s);
		
		ResponseEntity<String> responseEntity = null;
		try{
			
			responseEntity = bulidRestTemplate().exchange(url, HttpMethod.POST, getRequestEntity(entity), String.class);
			
		}catch(Exception e){
			
			LOG.info("sendPost error", e);
			
			throw new HttpExcetpion(4998);
		}
		
		reset();
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String jsonStr = responseEntity.getBody();
		
		LOG.debug("服务平台返回的数据:"+jsonStr);
		
		JsonNode node = null;
		
		try {
			node = objectMapper.readTree(jsonStr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return node;
	}
	
	//发送Post请求ForString
	public static <S> String sendPostForString(String url,S s) throws HttpExcetpion {
		
		LOG.info("服务调用,请求地址:"+url);
		
		posturl = url;
		
		ReqEntity entity = buildReqEntity(s);
		
		ResponseEntity<String> responseEntity = null;
		try{
			
			 responseEntity =  bulidRestTemplate().exchange(url, HttpMethod.POST, getRequestEntity(entity), String.class);
			
		}catch(Exception e){
			
			LOG.info("sendPostForString error", e);
			
			throw new HttpExcetpion(4998);
		}
		
		reset();
		
		responseEntity.getBody();
		
		return responseEntity.getBody();
	}
	
	private static void reset() {
		
		header = null;
		//默认请求数量
		pagesize = 10;
		//默认页码
		pagenum = 1;
	}
	
}
