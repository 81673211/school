package com.school.util.core.utils;

import com.school.util.core.exception.BusinessException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * 关闭流/Socket等操作的工具类.<BR>
 * 该类的方法均是在最终清理资源时使用, 那时已经不关心异常, 为调用者方便起见, 因此该类的方法不再抛出异常.
 * @author Administrator
 *
 */
public class CloseUtil {
	private static Logger log = Logger.getLogger(BusinessException.class);

	/**
	 * 关闭给定的输入流. <BR>
	 * 
	 * @param inStream
	 */
	public static void closeInputStream(InputStream inStream) {
		if (inStream != null) {
			try {
				inStream.close();
			} catch (IOException e) {
				log.error("error on close the inputstream.", e);
			}
		}
	}
}
