package com.school.util.core.utils;

import org.apache.commons.lang.StringUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;


/**
 * 模拟httpPost方式发送数据
 * 
 * @author Administrator
 * 
 */
public class HttpPostClient {/*

	*//**
	 * 发送http请求，支持http和https方式
	 * 
	 * @param serverUrl
	 *            请求地址
	 * @param realData
	 *            需要发送的数据
	 * @return
	 */
	public static String post(String serverUrl, String realData)
			throws Exception {
		if (StringUtils.isBlank(serverUrl) || StringUtils.isBlank(realData)) {
			throw new NullPointerException("请求地址或请求数据为空!");
		}
		// 分别调用http或https请求
		if (serverUrl.startsWith("https")) {
			return httpsPost(serverUrl, realData, 30000, 60000, "utf-8");
		} else {
			return httpPost(serverUrl, realData,  30000, 60000, "utf-8");
		}

	}

	/**
	 * https方式发送数据
	 * 
	 * @param serverUrl
	 *            请求地址
	 * @param realData
	 *            发送的数据
	 * @return1
	 * @throws Exception
	 */
	public static String httpsPost(String serverUrl, String realData,
			int connectTimeout, int readTimeout, String charset)
			throws Exception {

		MyX509TrustManager xtm = new MyX509TrustManager();
		MyHostnameVerifier hnv = new MyHostnameVerifier();
		ByteArrayOutputStream respData = new ByteArrayOutputStream();

		byte[] b = new byte[8192];
		SSLContext sslContext = null;
		sslContext = SSLContext.getInstance("TLS");
		X509TrustManager[] xtmArray = new X509TrustManager[] { xtm };
		sslContext.init(null, xtmArray, new java.security.SecureRandom());

		if (sslContext != null) {
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
					.getSocketFactory());
		}
		HttpsURLConnection.setDefaultHostnameVerifier(hnv);

		// 匹配https请求
		URLConnection conn = null;
		HttpsURLConnection httpsUrlConn = (HttpsURLConnection) (new URL(
				serverUrl)).openConnection();
		httpsUrlConn.setRequestMethod("POST");
		conn = httpsUrlConn;

		conn.setConnectTimeout(connectTimeout);
		conn.setReadTimeout(readTimeout);
		conn
				.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.1) Gecko/20061204 Firefox/2.0.0.3");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=" + charset);
		conn.setRequestProperty("connection", "close");
		conn.setDoOutput(true);
		conn.setDoInput(true);

		OutputStream out = conn.getOutputStream();
		out.write(realData.getBytes(charset));
		out.flush();
		out.close();

		InputStream is = conn.getInputStream();
		int len = 0;
		while (true) {
			len = is.read(b);
			if (len <= 0) {
				is.close();
				break;
			}
			respData.write(b, 0, len);
		}

		// 返回收到的响应数据
		return respData.toString(charset);
	}

	/**
	 * http方式发送数据
	 * 
	 * @param serverUrl
	 *            服务器地址
	 * @param realData
	 *            发送的数据
	 * @return 返回数据
	 */
	public static String httpPost(String serverUrl, String realData,
			int connectTimeout, int readTimeout, String charset)
			throws Exception {

		URL url = new URL(serverUrl);
		URLConnection urlconn = url.openConnection();
		urlconn.setDoOutput(true);
		urlconn.setConnectTimeout(connectTimeout);
		urlconn.setReadTimeout(readTimeout);
		urlconn
				.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.1) Gecko/20061204 Firefox/2.0.0.3");
		urlconn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded; charset=" + charset);
		urlconn.setRequestProperty("Connection", "close");

		OutputStream out = urlconn.getOutputStream();
		out.write(realData.getBytes(charset));
		out.flush();
		out.close();

		InputStream is = urlconn.getInputStream();
		int len = 0;
		ByteArrayOutputStream respData = new ByteArrayOutputStream();
		byte[] b = new byte[8192];
		while (true) {
			len = is.read(b);
			if (len <= 0) {
				is.close();
				break;
			}
			respData.write(b, 0, len);
		}

		return respData.toString(charset);
	}
	/**
	 * http get方式发送数据
	 * 
	 * @param serverUrl
	 *            服务器地址
	 * @param realData
	 *            发送的数据
	 * @return 返回数据
	 *//*
	public static String httpGet(String serverUrl, 
			int connectTimeout, int readTimeout, String charset)
			throws Exception {

		URL url = new URL(serverUrl);
		URLConnection urlconn = url.openConnection();
		urlconn.setConnectTimeout(connectTimeout);
		urlconn.setReadTimeout(readTimeout);
		urlconn
				.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.1) Gecko/20061204 Firefox/2.0.0.3");
		urlconn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded; charset=" + charset);
		urlconn.setRequestProperty("Connection", "close");

//		OutputStream out = urlconn.getOutputStream();
//		out.write(realData.getBytes(charset));
//		out.flush();
//		out.close();

		InputStream is = urlconn.getInputStream();
		int len = 0;
		ByteArrayOutputStream respData = new ByteArrayOutputStream();
		byte[] b = new byte[8192];
		while (true) {
			len = is.read(b);
			if (len <= 0) {
				is.close();
				break;
			}
			respData.write(b, 0, len);
		}

		return respData.toString(charset);
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws NoSuchAlgorithmException {
		List<String> md5List = new ArrayList<String>();
//		md5List.add("method=trade_query");
//		md5List.add("apiVersion=1.0.0.0");
//		// md5List.add("studentNo=liudechao");
//		// md5List.add("passwd=123456");
//		md5List
//				.add("sessionId=GJPmS7SL1Q3CnCQp8HpljKYHqmrKFH15TmTFpQLCM2cv5kbDvXc2!-199918469!1388040744888");
//		// md5List.add("startDate=2013-09-01");
//		// md5List.add("endDate=2013-12-01");
//		md5List.add("pageSize=2");
//		md5List.add("pageNo=2");
//		// md5List.add("paramId=rate");
//		// md5List.add("amt=100");
//		// md5List.add("payDefine=1006");
//		// md5List.add("orderNo=2013110814094160589");
//		//{"result":"0","desc":"交易成功","totalNum":"3","tradeList":[{"tradeTime":"2013-12-26 13:33:19","name":"手机测试1","tradeType":"1","amt":"100.19","feeAmt":"0.2"},{"tradeTime":"2013-12-26 13:33:05","name":"一卡通充值","tradeType":"0","amt":"100.2","feeAmt":"0.2"}]}
//		//{"result":"0","desc":"交易成功","totalNum":"3","tradeList":[{"tradeTime":"2013-12-26 13:32:41","name":"一卡通充值","tradeType":"0","amt":"50.1","feeAmt":"0.1"}]}
//
//		StringBuffer md5Src = new StringBuffer();
//		StringBuffer reqData = new StringBuffer();
//		Collections.sort(md5List, new Comparator() {
//			public int compare(Object o1, Object o2) {
//				String r1 = (String) o1;
//				String r2 = (String) o2;
//				return r1.compareTo(r2);
//				// return r1.hashCode() - r2.hashCode();
//			}
//		});
//		for (String str : md5List) {
//			md5Src.append(str);
//		}
//
//		md5Src.append("secret_key");
//		System.out.println("md5Src=[" + md5Src.toString() + "]");
//		MessageDigest md5 = MessageDigest.getInstance("MD5");
//		byte[] bytes = md5.digest(md5Src.toString().getBytes());
//		StringBuffer sb = new StringBuffer();
//		String temp = "";
//		for (byte b : bytes) {
//			temp = Integer.toHexString(b & 0XFF);
//			sb.append(temp.length() == 1 ? "0" + temp : temp);
//		}
//		for (String str : md5List) {
//			reqData.append(str);
//			reqData.append("&");
//		}
//		reqData.append("MD5=");
//		reqData.append(sb.toString());
		String url = "http://192.168.31.158:8080/MOBAOPAY_CASHIER/cgi-bin/netpayment/pay_gate.cgi?";
		String data = "apiName=REAL_CREDIT_PAY&apiVersion=1.0.0.0&platformID=MerchTest&merchNo=210001110100250&orderNo=2014010112345&tradeDate=20140101&amt=5&merchUrl=http://www.merchant.com/handler.jsp&merchParam=&tradeSummary=&cardName=测试&idCardNo=111111111111111111&cardBankCode=&cardType=2&cardNo=1111111111111111&cardExpire=0115&cardCvn2=151";
		
		try {
			
			String result =HttpPostClient.httpPost(url,data,60000,10000,"utf-8");
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/}

class MyX509TrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] chain, String authType) {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}

class MyHostnameVerifier implements HostnameVerifier {

	public boolean verify(String hostname, SSLSession session) {
		return true;
	}

}