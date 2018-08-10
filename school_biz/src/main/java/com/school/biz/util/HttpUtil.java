package com.school.biz.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.school.biz.exception.UtilException;

/**
 * Http请求工具类
 * 
 */
public class HttpUtil {

	/** 未知的标识 */
	public final static String UNKNOW = "unknown";

	public final static Pattern CHARSET_PATTERN = Pattern.compile("charset=(.*?)\"");

	private static Map<String, String> cookies = new HashMap<String, String>();
	
	private static Logger log = Logger.getLogger(HttpUtil.class);

	/**
	 * 编码字符为 application/x-www-form-urlencoded
	 * 
	 * @param content 被编码内容
	 * @return 编码后的字符
	 */
	public static String encode(String content, String charset) {
		if (StringUtils.isBlank(content)) return content;

		String encodeContent = null;
		try {
			encodeContent = URLEncoder.encode(content, charset);
		} catch (UnsupportedEncodingException e) {
			throw new UtilException(StrUtil.format("Unsupported encoding: [{}]", charset), e);
		}
		return encodeContent;
	}

	/**
	 * 解码application/x-www-form-urlencoded字符
	 * 
	 * @param content 被编码内容
	 * @return 编码后的字符
	 */
	public static String decode(String content, String charset) {
		if (StringUtils.isBlank(content)) return content;
		String encodeContnt = null;
		try {
			encodeContnt = URLDecoder.decode(content, charset);
		} catch (UnsupportedEncodingException e) {
			throw new UtilException(StrUtil.format("Unsupported encoding: [{}]", charset), e);
		}
		return encodeContnt;
	}

	/**
	 * 发送get请求
	 * 
	 * @param urlString 网址
	 * @param customCharset 自定义请求字符集，如果字符集获取不到，使用此字符集
	 * @param isPassCodeError 是否跳过非200异常
	 * @return 返回内容，如果只检查状态码，正常只返回 ""，不正常返回 null
	 * @throws IOException
	 */
	public static String get(String urlString, String customCharset, boolean isPassCodeError) throws IOException {
		final URL url = new URL(urlString);
		final String host = url.getHost();
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.83 Safari/537.1");
		final String cookie = cookies.get(host);
		if (cookie != null) conn.addRequestProperty("Cookie", cookie);

		conn.setRequestMethod("GET");
		conn.setDoInput(true);

		if (conn.getResponseCode() != 200) {
			if (!isPassCodeError) {
				throw new IOException("Status code not 200!");
			}
		}

		final String setCookie = conn.getHeaderField("Set-Cookie");
		if (!StringUtils.isBlank(setCookie)) {
			log.debug("Set cookie: ["+ setCookie +"]");
			cookies.put(host, setCookie);
		}

		/* 获取内容 */
		String charset = getCharsetFromConn(conn);
		boolean isGetCharsetFromContent = false;
		if (StringUtils.isBlank(charset)) {
			charset = customCharset;
			isGetCharsetFromContent = true;
		}
		String content = getString(conn.getInputStream(), charset, isGetCharsetFromContent);
		conn.disconnect();

		return content;
	}

	/**
	 * 发送post请求
	 * 
	 * @param urlString 网址
	 * @param paramMap post表单数据
	 * @param customCharset 自定义请求字符集，发送时使用此字符集，获取返回内容如果字符集获取不到，使用此字符集
	 * @param isPassCodeError 是否跳过非200异常
	 * @return 返回数据
	 * @throws IOException
	 */
	public static String post(String urlString, Map<String, Object> paramMap, String customCharset, boolean isPassCodeError) throws IOException {
		return post(urlString, toParams(paramMap), customCharset, isPassCodeError);
	}

	/**
	 * 发送post请求
	 * 
	 * @param urlString 网址
	 * @param params post表单数据
	 * @param customCharset 自定义请求字符集，发送时使用此字符集，获取返回内容如果字符集获取不到，使用此字符集
	 * @param isPassCodeError 是否跳过非200异常
	 * @return 返回数据
	 * @throws IOException
	 */
	public static String post(String urlString, String params, String customCharset, boolean isPassCodeError) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		//conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Type", "application/json");
		
		IoUtil.write(conn.getOutputStream(), customCharset, true, params);
		System.out.println(conn.getResponseCode());
		System.out.println(conn.getResponseMessage());
		if (conn.getResponseCode() != 200) {
			if (!isPassCodeError) {
				throw new IOException("Status code not 200!");
			}
		}

		/* 获取内容 */
		String charset = getCharsetFromConn(conn);
		String content = IoUtil.getString(conn.getInputStream(), StringUtils.isBlank(charset) ? customCharset : charset);
		conn.disconnect();

		return content;
	}

	/**
	 * 将Map形式的Form表单数据转换为Url参数形式
	 * 
	 * @param paramMap 表单数据
	 * @return url参数
	 */
	public static String toParams(Map<String, Object> paramMap) {
		return StringUtils.join(paramMap.entrySet(), "&");
	}

	// ----------------------------------------------------------------------------------------- Private method start
	/**
	 * 从Http连接的头信息中获得字符集
	 * 
	 * @param conn HTTP连接对象
	 * @return 字符集
	 */
	private static String getCharsetFromConn(HttpURLConnection conn) {
		String charset = conn.getContentEncoding();
		if (charset == null || "".equals(charset.trim())) {
			String contentType = conn.getContentType();
			charset = ReUtil.get("charset=(.*)", contentType, 1);
		}
		return charset;
	}

	/**
	 * 从流中读取内容
	 * 
	 * @param in 输入流
	 * @param charset 字符集
	 * @return 内容
	 * @throws IOException
	 */
	private static String getString(InputStream in, String charset, boolean isGetCharsetFromContent) throws IOException {
		StringBuilder content = new StringBuilder(); // 存储返回的内容

		// 从返回的内容中读取所需内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
		String line = null;
		while ((line = reader.readLine()) != null) {
			content.append(line).append('\n');
			if (isGetCharsetFromContent) {
				String charsetInContent = ReUtil.get(CHARSET_PATTERN, line, 1);
				if (!StringUtils.isBlank(charsetInContent)) {
					charset = charsetInContent;
					reader = new BufferedReader(new InputStreamReader(in, charset));
					isGetCharsetFromContent = true;
				}
			}
		}

		return content.toString();
	}
}
