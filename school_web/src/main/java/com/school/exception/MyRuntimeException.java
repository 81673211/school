package com.school.exception;

/**
 * 类名称：MyRuntimeException    
 * <br>类描述： 自定义运行时异常
 * <br>创建人：duanxiyong    
 * <br>创建时间：2016年9月27日 下午5:14:36    
 * <br>最后修改人：duanxiyong    
 * <br>最后修改时间：2016年9月27日 下午5:14:36    
 * <br>修改备注：    
 * <br>@version 1.0    
 *
 */
public class MyRuntimeException extends RuntimeException{

	/**    
	 * serialVersionUID:   
	 *    
	 * @since Ver 1.1    
	 */    
	
	private static final long serialVersionUID = -2340056808546765308L;

	public MyRuntimeException(Throwable cause) {
		super(cause);
	}

	public MyRuntimeException(String errorMsg) {
		super(errorMsg);
	}
	
	public MyRuntimeException(String errorMsg, Throwable cause) {
		super(errorMsg, cause);
	}
	
}
