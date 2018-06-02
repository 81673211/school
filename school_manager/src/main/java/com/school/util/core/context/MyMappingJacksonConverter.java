package com.school.util.core.context;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.school.constant.Constants;
import com.school.util.core.exception.RestException;
import com.school.util.core.utils.RSA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicReference;



/**
 * 自定义 JacksonConverter
 * @author fujie
 *
 */
@Component
public class MyMappingJacksonConverter extends AbstractHttpMessageConverter<Object>
  implements GenericHttpMessageConverter<Object>{
	
  private static final Logger LOG = LoggerFactory.getLogger(MyMappingJacksonConverter.class);
	
  public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

  private static final boolean jackson23Available = ClassUtils.hasMethod(ObjectMapper.class, "canDeserialize", new Class[] { JavaType.class, AtomicReference.class });

  private ObjectMapper objectMapper = new ObjectMapper();
  
/*  @Autowired
  private TokenInService userTokenService;*/
//  
//  @Autowired
//  private CustomerService customerService;
//  
//  private Token tk;
  
  
  //null值返回""
  /*
  {
		objectMapper.getSerializerProvider().setNullValueSerializer(
			new JsonSerializer<Object>() {
	
				@Override
				public void serialize(Object arg0, JsonGenerator arg1,
						SerializerProvider arg2) throws IOException,
						JsonProcessingException {
					
					arg1.writeString("");
				}
			});
  }
  */
  
  private String jsonPrefix;
  private Boolean prettyPrint;

  public MyMappingJacksonConverter(){
	  
    super(new MediaType[] { new MediaType("application", "json", DEFAULT_CHARSET), new MediaType("application", "*+json", DEFAULT_CHARSET) });
  }

  public void setObjectMapper(ObjectMapper objectMapper){
	  
    Assert.notNull(objectMapper, "ObjectMapper must not be null");
    this.objectMapper = objectMapper;
    configurePrettyPrint();
  }

  public ObjectMapper getObjectMapper(){
	  
    return this.objectMapper;
  }

  public void setJsonPrefix(String jsonPrefix){
	  
    this.jsonPrefix = jsonPrefix;
  }

  public void setPrefixJson(boolean prefixJson){
	  
    this.jsonPrefix = (prefixJson ? "{} && " : null);
  }

  public void setPrettyPrint(boolean prettyPrint){
	  
    this.prettyPrint = Boolean.valueOf(prettyPrint);
    configurePrettyPrint();
  }

  private void configurePrettyPrint() {
    if (this.prettyPrint != null)
      this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, this.prettyPrint.booleanValue());
  }

  public boolean canRead(Class<?> clazz, MediaType mediaType){
	  
    return canRead(clazz, null, mediaType);
  }

  public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType){
	  
    JavaType javaType = getJavaType(type, contextClass);
    if ((!jackson23Available) || (!this.logger.isWarnEnabled())) {
      return (this.objectMapper.canDeserialize(javaType)) && (canRead(mediaType));
    }
    AtomicReference causeRef = new AtomicReference();
    if ((this.objectMapper.canDeserialize(javaType, causeRef)) && (canRead(mediaType))) {
      return true;
    }
    Throwable cause = (Throwable)causeRef.get();
    if (cause != null) {
      String msg = "Failed to evaluate deserialization for type " + javaType;
      if (this.logger.isDebugEnabled()) {
        this.logger.warn(msg, cause);
      }
      else {
        this.logger.warn(msg + ": " + cause);
      }
    }
    
    return false;
  }

  public boolean canWrite(Class<?> clazz, MediaType mediaType){
	  
    if ((!jackson23Available) || (!this.logger.isWarnEnabled())) {
      return (this.objectMapper.canSerialize(clazz)) && (canWrite(mediaType));
    }
    AtomicReference causeRef = new AtomicReference();
    if ((this.objectMapper.canSerialize(clazz, causeRef)) && (canWrite(mediaType))) {
      return true;
    }
    Throwable cause = (Throwable)causeRef.get();
    if (cause != null) {
      String msg = "Failed to evaluate serialization for type [" + clazz + "]";
      if (this.logger.isDebugEnabled()) {
        this.logger.warn(msg, cause);
      }
      else {
        this.logger.warn(msg + ": " + cause);
      }
    }
    
    return false;
    
  }

  protected boolean supports(Class<?> clazz){
	  
    throw new UnsupportedOperationException();
  }

  protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
    throws IOException, HttpMessageNotReadableException{
	  
    JavaType javaType = getJavaType(clazz, null);
    return readJavaType(javaType, inputMessage);
  }

  public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
    throws IOException, HttpMessageNotReadableException{
	  
    JavaType javaType = getJavaType(type, contextClass);
    return readJavaType(javaType, inputMessage);
  }

  private Object readJavaType(JavaType javaType, HttpInputMessage inputMessage) {
	   
	  	//是否允许访问接口
//	  	Constants.suspendThreadSleep();
	  
	   String path = null;
	   try {
		   path = inputMessage.getHeaders().getLocation().getPath();
		   this.logger.info("############请求URI:"+path+"#################");
	   
	   }catch(Exception e){
		    throw new RuntimeException("Loaction->URI为空");
	   }
		
	    this.logger.info("Jackson...从JSON到对象...解压准备....");
	    
	    //1. 从 inputStream 中读取传输数据
	    byte[] bytes = null;
	    
	    try {
			bytes =  FileCopyUtils.copyToByteArray(inputMessage.getBody());
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		//2. 开始解密
		String jsonStr = "";
		try {
			try {
				this.logger.info("开始解压....");
				
				//解压缩
//				byte[] plainTextString = ZipUtils.unGZip(bytes);
//				try {
//					jsonStr = new String(plainTextString, "UTF-8");
//				} catch (UnsupportedEncodingException e1) {
//					e1.printStackTrace();
//				}
				
				jsonStr= new String(bytes,"UTF-8");
				
				this.logger.info("解压后JSON：" + jsonStr);
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			//3.验证Token
			String token = "";
			Long userId = null;
			if(filterPath(path)) {
				
				try{
					//解析JSON,获取token
					JsonNode Jsonode = objectMapper.readTree(jsonStr);
					token = Jsonode.get("header").get("token").asText();
					
					// 解密
					token = RSA.decryptByPrivateKey(token, Constants.USER_TOKEN_PRIVATEKEY);
				}catch(Exception e){				
					LOG.info("token解析异常", e);
					throw new RuntimeException("token不合法!");
				}
				
				//校验Token
				boolean ret = checkToken(token);
				//校验失败
				if(!ret){
					//throw new RuntimeException("token超时");
					throw new RestException("登录已超时!");
				}			
			}
			
		   //4. 将解压后的JSON串转换为对象
			return this.objectMapper.readValue(jsonStr, javaType);
		
		}  catch (IOException e) {
			
			LOG.debug("readJavaType error",e);
			
//			throw new HttpMessageNotReadableException("Could not read JSON: "
//					+ ex.getMessage(), ex);
			String str = e.getMessage();
			
			int start = str.indexOf("\"");
			int end = str.indexOf("\"", start+1);
			
			String errorKey = str.substring(start+1,end);
			
			throw new RestException("不匹配的请求参数:"+errorKey);
		
		}
  }

  protected void writeInternal(Object object, HttpOutputMessage outputMessage)
    throws IOException, HttpMessageNotWritableException {
   
	  this.logger.info("Jackson...从对象到JSON...压缩准备....");
	  
	  //1. 将对象转为JSON字符串
      String jsonStr = "";
      try {
    	  
    	  jsonStr = objectMapper.writeValueAsString(object);
    	  this.logger.info("压缩前的JSON:"+jsonStr);
    	  
      } catch (JsonGenerationException e) {
       e.printStackTrace();
      } catch (JsonMappingException e) {
       e.printStackTrace();
      } catch (IOException e) {
       e.printStackTrace();
      }
      
      //2. 开始压缩
	  String resultStr = "";
	  byte[] resultByte = null;
//	  try {
//			
//		  resultByte = ZipUtils.zip(jsonStr.getBytes("UTF-8"));
//			
//	  } catch (Exception e) {
//			e.printStackTrace();
//	  }
	  
	  resultByte = jsonStr.getBytes("UTF-8");
	  
	  this.logger.info("压缩后：" + new String(resultByte,"UTF-8"));
	  
	  //3. 将加密后的字符串输出到客户端
	  outputMessage.getBody().write(resultByte);
  }

  protected JavaType getJavaType(Type type, Class<?> contextClass){
	  
    return this.objectMapper.getTypeFactory().constructType(type, contextClass);
  }

  protected JsonEncoding getJsonEncoding(MediaType contentType){
	  
    if ((contentType != null) && (contentType.getCharSet() != null)) {
      Charset charset = contentType.getCharSet();
      for (JsonEncoding encoding : JsonEncoding.values()) {
        if (charset.name().equals(encoding.getJavaName())) {
          return encoding;
        }
      }
    }
    
    return JsonEncoding.UTF8;
    
  }
  
  /**
   * token 过期检查
   * @author fujie
   *
   */
  private boolean checkToken(String token){
	  
	 /* TokenIn tk = userTokenService.queryUserTokenByToken(token);
	  if(tk==null){
		  return false;
	  }else{		  
		  	//判断超时
			String date = tk.getValidateTime();
			DateTime invalidTime = DateUtil.parse(date);
			DateTime nowTime = DateUtil.parse(DateUtil.now());
			//如果当前时间 大于 设定时间 则 超时 处理
			if(nowTime.getTime() - invalidTime.getTime() >= 0){
				return false;
			}else{
				userTokenService.updateValidateTime(tk);
				return true;
			}
	  }*/
	  return false;
  }
  
  
  /**
   * token 过滤路径配置
   * @author fujie
   *
   */
  private boolean filterPath(String path) {
	  
	  String[] pathArr = new String[]{
			  "/token/get",
			  "/coupons/couponsInfo/addCouponsInfo"};
	  
	  for(int i=0;i<pathArr.length;i++){
		  if(path.indexOf(pathArr[i])>0){
			  return false;
		  }
	  }
	  
	  return true;
  }
  
}